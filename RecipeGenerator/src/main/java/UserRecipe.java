
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import java.io.BufferedReader;

import java.net.HttpURLConnection;
import org.json.JSONArray;
import org.json.JSONObject;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Hamza
 */
public class UserRecipe {

    String Email = "";
    DB_Connection_UserRecipe db;
    boolean Success = false;
    
    API api;
    /////////////////////////////////////////////////////////////////////////////////
    // for recipes generated va ingerdients
    JSONArray GenData; 
    ArrayList<Recipe> GenRecipe = new ArrayList<>();
    String GenDataFinal= ""; //data that will be sorted in to div blocks
    
    //for favorite recipes
    ArrayList<String> FavRecipeID = new ArrayList<>();
    ArrayList<Recipe> FavRecipe = new ArrayList<>();
    String FavDataFinal = "";
    
    //for Reccommended Recipes
    ArrayList<String> IngHistory = new ArrayList<>();
    ArrayList<String> FreqIng = new ArrayList<>();
    ArrayList<Integer> IntFreq = new ArrayList<>();
    JSONArray RecData;
    String RecDataFinal="";
    ArrayList<Recipe> RecRecipe = new ArrayList<>();
    
    
    /////////////////////////////////////////////////////////////////////////////////////
    UserRecipe(String em) {
        try {
            db = new DB_Connection_UserRecipe();
            String sql = "SELECT * FROM user_recipe WHERE Email = '" + em + "';";
            System.out.println(sql);
            db.err = sql;
            db.err = db.err + "</br>";
            ResultSet rs = db.st.executeQuery(sql);
            Email = em;
            //err = err + "statement executed";
            //err = err + rs.getString(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t" + rs.getString(4) + "\n";
            while (rs.next()) {
                FavRecipeID.add(rs.getString(1));
                //db. err = db.err + "login record found";
            }
            
            api = new API();
            getFavData();
            getFreqIng();
            
            //getFreqIng();
            //usr = new users(rs.getString(1), rs.getString(2), rs.getString(3), Integer.parseInt(rs.getString(4)));
            //err = err + "login record found";
        } catch (Exception e) {
            System.out.println(e);
            db.err = db.err + "</br>";
            db.err = db.err + e.getMessage();

        }
    }

    void getGenData(String req) throws JsonProcessingException {
        GenData = api.send_request(req);
        System.out.println(GenData.length());
        Recipe r;
        for (int i = 0; i < GenData.length(); i++) {
            
            JSONObject object = GenData.getJSONObject(i);
            
            String id = Integer.toString(object.getInt("id"));
            System.out.println(id);
            r = new Recipe(id);
            GenRecipe.add(r);
        }
        GenDataFinal ="<h2>Generated Recipes </h2>" + print_gen_recipe(GenRecipe);
    }
    
    void getFavData(){
        FavRecipe = new ArrayList<>();
        for(int i = 0 ; i < FavRecipeID.size(); i ++){
            Recipe r;
            r = new Recipe(FavRecipeID.get(i));
            System.out.println(FavRecipeID.get(i));
            FavRecipe.add(r);
        }
        System.out.println("getFavDAta();");
        FavDataFinal = "<h2>Favorite Recipes </h2>" + print_gen_recipe(FavRecipe);
    }
    
    
    void getRecData(String req) throws JsonProcessingException{
        
        RecData = api.send_request(req);
        System.out.println(RecData.length());
        Recipe r;
        for (int i = 0; i < RecData.length(); i++) {
            
            JSONObject object = RecData.getJSONObject(i);
            
            String id = Integer.toString(object.getInt("id"));
            System.out.println(id);
            r = new Recipe(id);
            RecRecipe.add(r);
        }
        RecDataFinal ="<h2>Recommended Recipes </h2>" + print_gen_recipe(RecRecipe);
    }
    

    String print_gen_recipe(ArrayList<Recipe> GenRecipe){
        String str = "";
        for(int i = 0; i < GenRecipe.size(); i ++){
            System.out.println(GenRecipe.get(i).id + GenRecipe.get(i).title + GenRecipe.get(i).sourceUrl);
            str = str + "<h3>" + GenRecipe.get(i).title + "<form method=\"GET\" action=\"apiServlet\"><input name=\"id\" "
                    + " type=\"hidden\" value=\"" + GenRecipe.get(i).id + "\">"
                    + "<input name=\"Email\" " + " type=\"hidden\" value=\""+Email+"\"/>"
                    + "<input type =\"submit\" name=\"AddFav\" value=\"Add/Remove\"/>" + "</form>" + "</h3>"
                    + "<a href=\"" + GenRecipe.get(i).sourceUrl + "\" target=\"_blank\">"
                    + "<img src=\"" + GenRecipe.get(i).img + "\">"
                    + "</a>";
            
        }
        System.out.println(str);
        return str;
    }
    
    
    void add_to_freq(ArrayList<String> ing) throws SQLException{
        for(int i = 0; i < ing.size(); i++){
            ing.get(i).replaceAll("\\s+","");
            if(!ing.get(i).equals("")){
                if(db.DB_add_ingredient(Email, ing.get(i)) == true){
                    System.out.println("ing added");
                    IngHistory.add(ing.get(i));
                }
                else{
                    System.out.println("ing failed to add");
                }
            }
        }
        getFreqIng();
    }
    
    void getFreqIng() throws SQLException{
        IngHistory = db.DB_get_ing(Email);
        System.out.println("getFreqIng called");
        ArrayList<String> visited = new ArrayList<>();
        for(int i = 0 ; i < IngHistory.size() ; i ++){
            int count = 0;
            String ing = IngHistory.get(i);
            if(!visited.contains(ing)){
                visited.add(ing);
                for(int j = i ; j < IngHistory.size() ; j ++){
                    if(ing.equals(IngHistory.get(j))){
                        count++;
                    }
                }
                FreqIng.add(ing);
                IntFreq.add(count);
                System.out.println(ing + ", " + count);
            }
            
        }
        System.out.println("getFreqIng 1");
        for(int i = 0; i < FreqIng.size(); i ++){
            for(int j = 0 ; j < FreqIng.size(); j ++){
                if(IntFreq.get(i)<IntFreq.get(j)){
                    //swap int
                    int temp = IntFreq.get(i);
                    IntFreq.set(i, IntFreq.get(j));
                    IntFreq.set(j, temp);
                    
                    
                    //swap string
                    String temp1 = FreqIng.get(i);
                    FreqIng.set(i, FreqIng.get(j));
                    FreqIng.set(j, temp1);
                }
            }
        }
        System.out.println("getFreqIng 2");
        for(int i = 0; i < FreqIng.size(); i ++){
            System.out.println(FreqIng.get(i) + ", " + IntFreq.get(i));
        }
        System.out.println("getFreqIng completed");
    }
    
    
    
    void addFav(String id){
        System.out.println("add fav called");
        if(FavRecipeID.contains(id)){
            db.DB_remove_fav(Email, id);
            FavRecipeID.remove(id);
            getFavData();
        }
        else{
            db.DB_add_fav(Email, id);
            FavRecipeID.add(id);
            getFavData();
        }
        System.out.println("add fav completed");
    }
    
}

class DB_Connection_UserRecipe {

    Connection con;
    Statement st;
    String err = "";

    DB_Connection_UserRecipe() {

        try {

            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            //Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            //con = DriverManager.getConnection("jdbc:odbc:Database1");
            con = DriverManager.getConnection("jdbc:ucanaccess://D:\\FAST\\Spring 2021\\Advanced Programming\\Project\\RecipeGenerator\\food_app_db.accdb");
            st = con.createStatement();//D:\FAST\Spring 2021\Advanced Programming\Project\foodapp_jsp
            err = "connected to db";
            String sql = "Select * from user_recipe";
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {

                //err = err + rs.getString(1)+ "\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t" + rs.getString(4) + "\n";
                System.out.println(rs.getString(1) + "\t" + rs.getString(2));
            }

        } catch (Exception e) {
            System.out.println(e);
            System.out.println("failed to connect to db");
            err = e.getMessage();
        }
    }
    
    
    boolean DB_add_ingredient(String Email, String ing){
        try{
            String sql = "INSERT INTO frequent (email, ingredient) VALUES ('" + Email + "', '" + ing+ "');";
            System.out.println(sql);
            boolean rs = st.execute(sql);
            
            return true;
        }
        catch(Exception e){
            System.out.println(e);
            return false;
        }
        
    }
    
    ArrayList<String> DB_get_ing(String email) throws SQLException{
        ArrayList<String> ing = new ArrayList<>();
        String sql = "SELECT ingredient FROM frequent WHERE email = '" + email + "';";
        ResultSet rs = st.executeQuery(sql);
        while (rs.next()) {

            //err = err + rs.getString(1)+ "\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t" + rs.getString(4) + "\n";
            ing.add(rs.getString(1));
            System.out.print(rs.getString(1));
        }
        
        return ing;
    }
    
    
    boolean DB_add_fav(String Email, String Rec) {
        try {
            String sql = "INSERT INTO user_recipe (email, recipe) VALUES ('" + Email + "', '" + Rec + "');";
            System.out.println(sql);
            boolean rs = st.execute(sql);

            return true;
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }
    }
    
    boolean DB_remove_fav(String Email , String Rec){
        try {
            String sql = "DELETE FROM user_recipe WHERE email='"+ Email + "' AND recipe='"+ Rec + "';";
            System.out.println(sql);
            boolean rs = st.execute(sql);

            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }
    
    

}

class API {

    String baseurl = "https://api.spoonacular.com/recipes/";
    String apikey = "&ranking=2&number=5&apiKey=eeb4fcda2bab4a2d83ab259f63621642";
    URL url;
    HttpURLConnection conn;
    

    API() {
        System.out.println("API const");
    }

    //https://api.spoonacular.com/recipes/findByIngredients?ingredients=apples,+flour,+sugar&ranking=2&number=5&apiKey=7fab14b8039e45ff8d2d8ad94f3e3bc5
    JSONArray send_request(String req) throws JsonProcessingException {
        String data = "";
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().GET().header("accept", "application/json").uri(URI.create(req)).build();
        HttpResponse<String> response = client.sendAsync(request, HttpResponse.BodyHandlers.ofString()).join();
        System.out.println(req);
        System.out.println(response.body());
        /*ObjectMapper mapper = new ObjectMapper();
        List<Recipe> recipe = mapper.readValue(response.body(), new TypeReference<List<Recipe>>(){});
        for(int i = 0; i < recipe.size() ; i ++){
            
        }*/

        JSONArray array = new JSONArray(response.body());

        return array;
    }
}

class Recipe {

    String title = "";
    String img = "";
    String sourceUrl = "";
    String id = "";

    Recipe(String ID) {

        id = ID;
        ///https://api.spoonacular.com/recipes/716429/information?apiKey=dab1c331b016493681a18bfcc75420b3"
        String qry = "https://api.spoonacular.com/recipes/";
        qry = qry + id + "/information?apiKey=dab1c331b016493681a18bfcc75420b3";
        System.out.println(qry);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().GET().header("accept", "application/json").uri(URI.create(qry)).build();
        HttpResponse<String> response = client.sendAsync(request, HttpResponse.BodyHandlers.ofString()).join();
        JSONObject object = new JSONObject(response.body());

        System.out.println(response.body());
        
        
            
        title = object.getString("title");
        img = object.getString("image");
        sourceUrl = object.getString("sourceUrl");
        //System.out.println(id);
        

    }

    
}
