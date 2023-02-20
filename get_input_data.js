
// const apiKey = '5dc1af0db1a94324a269c6ee58753591';
// apiKey = " 129b05227cb3447e8f24b1da59e5da75";

var ingredients_array = [];

function get_input_data() {
    var ingredients = document.getElementsByClassName("ingredient");
    for (i = 0; i < ingredients.length; i++) {
        ingredients_array.push(this.state.ingredients[i].value) ;
        // ingredients_array[i]=ingredients[i].value ;

        return ingredients_array;
    }
}

ingredients_array = ['sugar', 'milk', 'flour','honey','cinnamon'];

const apiKey = '5dc1af0db1a94324a269c6ee58753591';

async function get_info() {
  try {
    // ingredients_array = ['sugar', 'water', 'flour'];
    const data = await axios({
                   method: 'get',
                   url: 'https://api.spoonacular.com/recipes/findByIngredients',

                   params: {
                     apiKey: apiKey,
                     ingredients: ingredients_array.join(',+'),
                     number: '2'
                   },
                 }).then((response) => {
      console.log(response.data);
      return response.data;
    });

    return data;
  } catch (err) {
    return (err.message);
  }
}




   