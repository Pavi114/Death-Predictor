var addMoreItems = document.querySelector("#addMoreFood");
var removeFood = document.querySelector("#deleteFood");
var submit = document.querySelector("#submit");
var form = document.querySelector("#userDetails");
var hidden = document.querySelector(".hidden");
var clear1 = document.querySelector("#clear1");
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
var food = 0;
var req = 0;
var once = 0;
var once1 = 0;
var once2 = 0;
var once3 = 0;

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

	displayCalReq(){
		if(!req){
		var line = document.querySelector("#line1");
		var text = document.querySelector("#calreqRes");
		if(!once){
          var line2 = document.createElement("HR");
		  text.classList.add("printResult");
		  text.parentNode.insertBefore(line2,text.nextSibling);
		  once = 1;
		}
		text.innerHTML = "Your daily Calorie requirement is " + this.calorieRequire + " Calories";
		req = 1;	
		}
		
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

var calReq = new calorieCounter("",0,0,0,0,"",0,0);

//adding food items
addMoreItems.addEventListener("click",function(){

	var food = document.querySelector("#food");
	var carbs = document.getElementById('carbs');
	var fats = document.querySelector("#fats");
	var proteins = document.querySelector("#proteins");
    
	if(!(food.value == "" || carbs.value == ""
		|| fats.value == "" || proteins.value == "")){

      if(!(parseFloat(carbs.value) <= 0 || parseFloat(fats.value) <= 0 || parseFloat(proteins.value) <= 0)){

      	var foodDetails = new calorieIntake(food.value,parseFloat(carbs.value),parseFloat(fats.value),parseFloat(proteins.value));
	    foodDetails.calCaloriePerFood();
	    foodList.push(foodDetails);
          cal = 0;
         if(itemsArray.length >= 1){
         	calReq = itemsArray[itemsArray.length - 1];
            calReq.calIntake += foodDetails.caloriePerFood;
	        itemsArray.push(calReq);
	        var msg = cmpCal();
            displayCal(msg);
         }

        displayFood(foodList.length - 1);
	    localStorage.setItem("foods",JSON.stringify(foodList));
	    localStorage.setItem("items",JSON.stringify(itemsArray));
	    foodReset();

      }
      else{
      	alert("Please enter valid values");
      }
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
				var table = document.querySelector("table");
				table.deleteRow(i + 1);
				foodList.splice(i,1);
                if(foodList.length == 0){
                	table.deleteRow(0);
                }
				flag = 1;
				foodName.value = "";
				removeFoodDiv.classList.add("hidden");
			}
		}
		if(flag){
			var msg = cmpCal();
			displayCal(msg);
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



//calculate calorie requirement
submit.addEventListener("click",function(){

	var name = document.querySelector("#name");
	var age = document.querySelector("#age");
	var gender = genderFind();
	var height = document.querySelector("#height");
	var weight = document.querySelector("#weight");
	var phyLevel = document.querySelector("#physicalLevel");
	
	if(!(name.value == "" || parseInt(age.value) <= 0 || parseFloat(height.value) <= 0 
		|| parseFloat(weight.value) <= 0 || gender == "" || parseFloat(phyLevel.value) <= 0 )){
             req = 0;
			var calReqTemp = new calorieCounter(name.value,parseInt(age.value),parseFloat(height.value),parseFloat(weight.value),parseFloat(phyLevel.value),gender,0,0);
			calReq = calReqTemp;
			calReq.calcCalorieRequired();
            calReq.displayCalReq();
            
            //pushing into local storage
			itemsArray.push(calReq);
			localStorage.setItem('items', JSON.stringify(itemsArray));

		}

	else{
		alert("Please Enter Valid Values!");
	}
});

//water tracker
submitW.addEventListener("click",function(){
	var water = document.querySelector("#water");
	if(!(itemsArray.length == 0 || itemsArray[itemsArray.length - 1].gender == "" )){
		if(!(water.value <= 0)){
			calReq = itemsArray[itemsArray.length - 1];
           calReq.water += parseFloat(water.value);
	var msg = cmpWater();
    itemsArray.push(calReq);
	localStorage.setItem('items', JSON.stringify(itemsArray));
	displayWaterReq(msg);
		}
		else{
			alert("Please enter a positive number");
		}
		
	}
	else{
		alert("Please Enter basic details first");
	}
	
})

//clear input field
clear1.addEventListener("click",function(){	
	localStorage.clear();

	userDetailReset();
	foodReset();
	var table = document.querySelector("table");
	if(foodList.length > 0){
	for(var i = foodList.length;i >= 0;i--){
		table.deleteRow(i);
	}
	}
	
	document.querySelector("#calRes").innerHTML = "";
	document.querySelector("#calRes").classList.remove(".printResult");
	document.querySelector("#waterRes").innerHTML = "";
	document.querySelector("#waterRes").classList.remove(".printResult");
	document.querySelector("#calreqRes").innerHTML = "";
	document.querySelector("#calreqRes").classList.remove(".printResult");
	foodList = [];
	itemsArray = [];
	food = 0;
	
});

function cmpCal(){
		var msg;
		if(calReq.calorieRequire == calReq.calIntake){
			alert("Yayy! You take in the right amount of calories");
			msg = "Whoa Whoa! You take in the right amount of calorie. Maintain the same level everyday to lead a healthy Life";
		}
		else if(calReq.calorieRequire < calReq.calIntake){
			alert("Oof You have to reduce calorie intake");
			msg = "You have to reduce your calorie Intake by " + Math.round((calReq.calIntake - calReq.calorieRequire) * 100)/100 + " cal.";
		}
		else {
            alert("Oof you have to increase calorie intake");
			msg = "You have to increase your calorie Intake by " + Math.round((calReq.calorieRequire - calReq.calIntake) * 100)/100 + " cal";
		}

		return msg;
	}

function displayWaterReq(msg){
	    if(!wat){
		var text = document.querySelector("#waterRes");
		text.classList.add("printResult");
		var element = document.querySelector("#line2");
		if(!once2){
		var line2 = document.createElement("HR");	
		text.parentNode.insertBefore(line2,text.nextSibling);	
		once2 = 1;
		}
		wat = 1;
	    text.innerHTML = msg;
	   }
}


function cmpWater(){
		var msg;
		wat = 0;
		if(calReq.water >= 3.7 && calReq.gender == "Male" || calReq.water >= 2.7 && calReq.gender == "Female"){
			msg = "Yayy! You consume the right amount of water.";
			alert("Good Job! You consume the right amount");
		}
		else
		{
			msg = "Human body requires atleast 3.7 litres of water for males and 2.7 litres of water for females on a daily basis.";
			msg += "Not Drinking enough water can lead to dehydration which causes Dizziness,change in urine colour,kidney problems etc.";
			msg += "Please maintain the minimum level of water consumption."; 
			alert("Uh-Oh! Please increase your water intake.");
		}
		return msg;
	}
function displayCal(msg){
	    if(!cal){
	    var element = document.querySelector("#line3");
	    var text = document.querySelector("#calRes");
	    text.classList.add("printResult");
	    if(!once3){
	    var line2 = document.createElement("HR");	
		text.parentNode.insertBefore(line2,text.nextSibling);	
		once3 = 1;
	    }
		text.innerHTML = msg;
	    }
	    
}


function displayFood(index){

		    var table = document.querySelector("table");
		    table.classList.add("printResult");
		    if(!food){
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
		         food = 1;
		    }

		    var row = table.insertRow(index  + 1);
			var cell1 = row.insertCell(0);
			var cell2 = row.insertCell(1);
			var cell3 = row.insertCell(2);
			var cell4 = row.insertCell(3);
			var cell5 = row.insertCell(4);

			cell1.innerHTML = foodList[index].food;
			cell2.innerHTML = foodList[index].carbs.toString();
			cell3.innerHTML = foodList[index].fats.toString();
			cell4.innerHTML = foodList[index].proteins.toString();
			cell5.innerHTML = foodList[index].caloriePerFood.toString();

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
        for(var i = 0; i < foodList.length;i++){
               displayFood(i);	
        }
        
	}

}


