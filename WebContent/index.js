//function loadDoc() {
//  var xhttp = new XMLHttpRequest();
//  xhttp.onreadystatechange = function() {
//    if (this.readyState == 4 && this.status == 200) {
//     document.getElementById("demo").innerHTML = this.responseText;
//    }
//  };
//  xhttp.open("GET", "/LibraryManagementSystem/v1/book/show", true);
//  xhttp.send();
//	let address = undefined;
//	let some = fetch("http://localhost:8080/LibraryManagementSystem/v1/book/").then((response) => response.json()).then((user) => {    
//	    fun(user);
//	    return user[0].isbn;
//	  });
//
//}
//
// function fun(data){
//	document.getElementById("demo").innerHTML = data[0].isbn;
//	
//}

//async function fetchMoviesJSON() {
//  const response = await fetch("http://localhost:8080/LibraryManagementSystem/v1/book/");
//  const movies = await response.json();
//  return movies;
//}
//
//
//document.getElementById("form").addEventListener('submit',function(e){
//
//	fetchMoviesJSON().then(movies => {
//  console.log(movies.length); // fetched movies
//  
//});
//	e.preventDefault();
//})