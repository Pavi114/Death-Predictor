var addMoreItems = document.querySelector("#addMoreFood");
var removeFood = document.querySelector("#deleteFood");
var submit = document.querySelector("#submit");
var form = document.querySelector("#userDetails");
var hide = document.querySelector(".hide");
var hidden = document.querySelector(".hidden");
var result = document.getElementById("printResult");
var clear1 = document.querySelector("#clear1");
var clear2 = document.querySelector("#clear2");
var foodItemList = document.querySelector("#foodItemList");
var btn = document.querySelector("#remove"); 

//getting items from local storage if exists
var itemsArray = localStorage.getItem("items") ? JSON.parse(localStorage.getItem("items")) : [];
var foodList = localStorage.getItem("foods") ? JSON.parse(localStorage.getItem("foods")) : [];

//if no items exist, then creating two new items 
localStorage.setItem("items",JSON.stringify(itemsArray));
localStorage.setItem("foods",JSON.stringify(foodList));

var data = JSON.parse(localStorage.getItem("items"));
foodList = JSON.parse(localStorage.getItem("foods"));

//variables used for checking later on
var cal = 0;
var wat = 0;
var once = 0;

//Class to store basic user info
class calorieCounter {

	constructor(name,age,height,weight,phyLevel,gender,calIntake,water) {
		this.age = age;
		this.heights = height;
		this.weight = weight;
		this.username = name;
		this.gender = gender;
		this.calIntake = calIntake;
		this.water = water;
		this.phyLevel = phyLevel;

	}
	getName(){
		return this.username;
	}
	
	getAge(){
		return this.age;
	}

	getWeight(){
		return this.weight;
	}

	getHeight(){
		return this.heights;
	}

	getGender()
	{
		return this.gender;
	}

	getCalorieRequired(){
		return this.calorieRequire;
	}

	getCalorieIntake(){
		return this.calIntake;
	}
    
    //calculates required calorie
    //Formula used: Harris Benedict Formula
	calcCalorieRequired(){
		var calorie;

		if(this.gender === "Male"){
			calorie = 66.5 + (13.8 * this.weight) + (5 * this.heights) - (6.8 * this.age);
		}
		else if(this.gender === "Female"){
			calorie = 655.1 + (9.6 * this.weight) + (1.9 * this.heights) - (4.7 * this.age);
		}

		this.calorieRequire = Math.round(calorie * this.phyLevel * 100)/100;
	}
    
    //function to decrement calorie intake upon removal of an item
	decrement(caloriePerFood){
		this.calIntake -= caloriePerFood;
	}

    //comparing req. and consumed calorie
	cmpCal(){
		var msg;
		if(this.calorieRequire == this.calIntake){
			cal = 2;
			msg = "Whoa Whoa! You take in the right amount of calorie. Maintain the same level everyday to lead a healthy Life";
		}
		else if(this.calorieRequire < this.calIntake){
			cal = 1;
			msg = "You have to reduce your calorie Intake by " + Math.round((this.calIntake - this.calorieRequire) * 100)/100 + " cal.";
		}
		else {

			msg = "You have to increase your calorie Intake by " + Math.round((this.calorieRequire - this.calIntake) * 100)/100 + " cal";
		}

		return msg;
	}
    
    //comapring qty of water consumed
	cmpWater(){
		var msg;
		if(this.water >= 3.7 && this.gender == "Male" || this.water >= 2.7 && this.gender == "Female"){
			msg = "Yayy! You consume the right amount of water.";
		}
		else
		{
			wat = 1;
			msg = "Human body requires atleast 3.7 litres of water for males and 2.7 litres of water for females on a daily basis. you need to consume. ";
			msg += "Not Drinking enough water can lead to dehydration which causes Dizziness,change in urine colour,kidney problems etc.";
			msg += "Please maintain the minimum level of water consumption. " 
		}
		return msg;
	}

}

//class to store foodlist
class calorieIntake {
	constructor(food,carbs,fats,proteins){
		this.food = food;
		this.carbs = carbs;
		this.fats = fats;
		this.proteins = proteins;

	}
    
    //calculates calorie intake
	calcCalorieIntake(){

		var dailyCalorieIntake = 0;

		for(var i = 0; i < foodList.length;i++){

			dailyCalorieIntake += foodList[i].caloriePerFood;
		}

		return parseFloat(dailyCalorieIntake.toString());
	}

	calCaloriePerFood(){
		const CARB_CALORIE = 4.0;
		const PROTEIN_CALORIE = 4.0;
		const FAT_CALORIE = 9.0;

		this.caloriePerFood = this.carbs * CARB_CALORIE + this.proteins * PROTEIN_CALORIE + this.fats * FAT_CALORIE;
	}
}

//used to set initial value to form inputs from local storage
initialValue();

//adding food items
addMoreItems.addEventListener("click",function(){

	var food = document.querySelector("#food");
	var carbs = document.getElementById('carbs');
	var fats = document.querySelector("#fats");
	var proteins = document.querySelector("#proteins");

	if(!(food.value == "" || parseFloat(carbs.value) <= 0
		|| parseFloat(fats.value) <= 0 || parseFloat(proteins.value) <= 0)){

		var foodDetails = new calorieIntake(food.value,parseFloat(carbs.value),parseFloat(fats.value),parseFloat(proteins.value));
	foodDetails.calCaloriePerFood();
	foodList.push(foodDetails);

	localStorage.setItem("foods",JSON.stringify(foodList));
	foodReset();

}
else{
	alert("Please enter valid values");
}


});


//removing food items
removeFood.addEventListener("click",function(){
	var removeFoodDiv = document.querySelector("#removefood");
	if(foodList.length >= 1 && itemsArray.length < 1){
		alert("Please save your basic details before removing. (Click calculate calories)");
		removeFoodDiv.classList.add("hidden");
	}
	else if(foodList.length >= 1){
		removeFoodDiv.classList.toggle("hidden");
	}
	else{
		alert("There are no items in your Food List to remove");
		removeFoodDiv.classList.add("hidden");
	}

});

//removing food items
btn.addEventListener("click",function(){
	var removeFoodDiv = document.querySelector("#removefood");
	var flag = 0;
	var foodName = document.querySelector("#foodName");
	if(!(foodName.value == "")){
		var i;
		for (i = 0;i < foodList.length; i++){
			if(foodList[i].food.toUpperCase() == foodName.value.toUpperCase()){
				itemsArray[itemsArray.length-1].calIntake -= foodList[i].caloriePerFood;
				foodList.splice(i,1);
				flag = 1;
				foodName.value = "";
				removeFoodDiv.classList.add("hidden");
			}
		}
		if(flag){
			alert("Removed Successfully");
			localStorage.setItem("foods",JSON.stringify(foodList));
			localStorage.setItem("items",JSON.stringify(itemsArray));
		}
		else {
			alert("Oops! Looks like this item is not part of your daily food Intake");
			foodName.value = "";
		}
	}
	else{
		alert("Please enter Valid Values");
	}

})



//submit form to calculate and compare calories
submit.addEventListener("click",function(){

	var name = document.querySelector("#name");
	var age = document.querySelector("#age");
	var gender = genderFind();
	var height = document.querySelector("#height");
	var weight = document.querySelector("#weight");
	var phyLevel = document.querySelector("#physicalLevel");
	var water = document.querySelector("#water");

	var food = document.querySelector("#food");
	var carbs = document.getElementById('carbs');
	var fats = document.querySelector("#fats");
	var proteins = document.querySelector("#proteins");


	if(!(name.value == "" || parseInt(age.value) <= 0 || parseFloat(height.value) <= 0 
		|| parseFloat(weight.value) <= 0 || parseFloat(water.value) <= 0 
		|| gender == "" || parseFloat(phyLevel.value) <= 0 )){

		if(!(food.value == "" && carbs.value == 0 && proteins.value == 0 && fats.value == 0)){

			var foodDetails = new calorieIntake(food.value,parseFloat(carbs.value),parseFloat(fats.value),parseFloat(proteins.value));
			foodDetails.calCaloriePerFood();
			foodList.push(foodDetails);
		}
		if(!(foodList.length < 1)){
			var alt = new calorieIntake();			
			var dailyIntake = alt.calcCalorieIntake();


			var calReq = new calorieCounter(name.value,parseInt(age.value),parseFloat(height.value),parseFloat(weight.value),parseFloat(phyLevel.value),gender,dailyIntake,parseFloat(water.value));

			calReq.calcCalorieRequired();

			display(calReq);


			hide.classList.add("hidden");
			result.classList.remove("hidden");

			if(cal == 1 && wat == 1){

				alert("Uh-Oh! You consume more than required amount of calories and water on a daily basis");	
			}
			else if(cal == 0 && wat == 1){
				alert("Uh-Oh! You need to consume less calories and more water in a day");
			}
			else if(cal == 1){
				alert("Uh-Oh! You consume more than required amount of calories in a day.");
			}
			else if(wat == 1){

				alert("Uh-Oh! You did not consume enough water today");	
			}
			else if(cal == 0){
				alert("Uh-Oh! You consume less calories than what is required");
			}
            
            //pushing into local storage
			itemsArray.push(calReq);
			localStorage.setItem('items', JSON.stringify(itemsArray));

		}
		else{
			alert("Please add food Items");
		}
	}

	else{
		alert("Please Enter Valid Values!");
	}
});




//clear input field
clear1.addEventListener("click",function(){	
	reset();
});

//display food list
foodItemList.addEventListener("click",function(){

	if(!once){
		once = 1;
		var para = document.querySelector("#foodItem");
		var table = document.createElement("TABLE");
		para.appendChild(table);

		var row = table.insertRow(0);

		var head1 = row.insertCell(0);
		var head2 = row.insertCell(1);
		var head3 = row.insertCell(2);
		var head4 = row.insertCell(3);
		var head5 = row.insertCell(4);
		head1.innerHTML = "Food Item";
		head2.innerHTML = "Carbs";
		head3.innerHTML = "Fats";
		head4.innerHTML = "Proteins";
		head5.innerHTML = "Calories";


		for(var i = 0;i<foodList.length;i++){
			row = table.insertRow(i + 1);
			var cell1 = row.insertCell(0);
			var cell2 = row.insertCell(1);
			var cell3 = row.insertCell(2);
			var cell4 = row.insertCell(3);
			var cell5 = row.insertCell(4);

			cell1.innerHTML = foodList[i].food;
			cell2.innerHTML = foodList[i].carbs.toString();
			cell3.innerHTML = foodList[i].fats.toString();
			cell4.innerHTML = foodList[i].proteins.toString();
			cell5.innerHTML = foodList[i].caloriePerFood.toString();
		}
	}
});

//reset values
clear2.addEventListener("click",function(){
	reset();
});


function reset(){
	localStorage.clear();

	userDetailReset();
	foodReset();
	foodList = [];
	itemsArray = [];
	var foodItem = document.querySelector("#foodItem");
	foodItem.removeChild(foodItem.lastChild);
	
	form.classList.remove("hidden");
	result.classList.add("hidden");
}

//used to get gender of user
function genderFind(){
	if(document.getElementById('genderMale').checked){
		return document.getElementById("genderMale").value;
	}
	else if(document.getElementById("genderFemale").checked){
		return document.getElementById("genderFemale").value;
	}
	else
		return "";
}

//reset food details
function foodReset(){
	var food = document.querySelector("#food");
	var carbs = document.getElementById('carbs');
	var fats = document.querySelector("#fats");
	var proteins = document.querySelector("#proteins");

	food.value = "";
	carbs.value = 0;
	fats.value = 0;
	proteins.value = 0;
}

//reset user details
function userDetailReset(){
	var name = document.querySelector("#name");
	var age = document.querySelector("#age");
	var height = document.querySelector("#height");
	var weight = document.querySelector("#weight");
	var phyLevel = document.querySelector("#physicalLevel");
	var water = document.querySelector("#water");

	document.getElementById("genderMale").checked = false;
	document.getElementById("genderFemale").checked = false;
	name.value = "";
	age.value = 0;
	height.value = 0;
	weight.value = 0;
	water.value = 0;
	phyLevel.value = "0";
}

function initialValue(){

	var name = document.querySelector("#name");
	var age = document.querySelector("#age");
	var radios = document.getElementsByName("gender"); 
	var height = document.querySelector("#height");
	var weight = document.querySelector("#weight");
	var phyLevel = document.querySelector("#physicalLevel");
	var water = document.querySelector("#water");

	var food = document.querySelector("#food");
	var carbs = document.getElementById('carbs');
	var fats = document.querySelector("#fats");
	var proteins = document.querySelector("#proteins");

	var len = data.length - 1;
	var lenF = foodList.length - 1;

	if(len >= 0){

		name.value = data[len].username;
		age.value = data[len].age;
		height.value = data[len].heights;
		weight.value = data[len].weight;
		water.value = data[len].water;

		for(var i = 0;i < radios.length; i++){
			if(radios[i].value == data[len].gender){
				radios[i].checked = true; 
			}
		}

		phyLevel.value = data[len].phyLevel;

	}

}


function display(calReq){

	var printName = document.querySelector("#printName");
	var printAge = document.querySelector("#printAge");
	var printGender = document.querySelector("#printGender");
	var printCalReq = document.querySelector("#printCalReq");
	var printCalIn = document.querySelector("#printCalIn");
	var printCalInfer = document.querySelector("#printCalInfer");
	var printWaterInfer = document.querySelector("#printWaterInfer");

	printName.innerHTML = "Name: " + calReq.getName();
	printAge.innerHTML = "Age: " + calReq.getAge();
	printGender.innerHTML = "Gender: " + calReq.getGender();
	printCalReq.innerHTML = "Daily Calorie Required: " + calReq.getCalorieRequired() + " Cal";
	printCalIn.innerHTML = "Daily Calorie Intake: " + calReq.getCalorieIntake() + " Cal";
	printCalInfer.innerHTML = calReq.cmpCal();
	printWaterInfer.innerHTML = calReq.cmpWater();
}

