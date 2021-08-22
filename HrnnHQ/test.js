// vars to store user entered surve data
var cupReccomend = "";
var excerciseReccomend = "";
var sleepReccomend = "";

// Function to analyze and interpret data
function analyzeData() {
  // if water cups drank per day is less than 12, recommend drinking more cups
  if (document.getElementById("0-4 cups").checked) {
    cupReccomend =
      "You should drink more water! People should drink around 13.5 cups of water per day!";
  }
  if (document.getElementById("5-8 cups").checked) {
    cupReccomend =
      "You should drink more water! People should drink around 13.5 cups of water per day!";
  }
  if (document.getElementById("9-12 cups").checked) {
    cupReccomend =
      "You should drink more water! People should drink around 13.5 cups of water per day!";
  }

  // if weekly exercise is less than 2 hours, recommend more exercise
  if (document.getElementById("0-1 hours").checked) {
    excerciseReccomend =
      "You should excercise more! People should work out at least 2 hours a week!";
  }
  if (document.getElementById("1-2 hours").checked) {
    excerciseReccomend =
      "You should excercise more! People should work out at least 2 hours a week!";
  }

  // hours hours slept er night is less than 8 hrs recommend sleeping more hours
  if (document.getElementById("less than 6 hours").checked) {
    sleepReccomend =
      "Make sure to get a good night's rest! People should sleep for around 8 hours a night!";
  }
}

function p() {
  //if (cupReccomend == "" && excerciseReccomend == "" && sleepReccomend == "") {
  document.write("testing stuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuff");

  //   Uncaught ReferenceError: p is not defined
  //     at onload (add_info_results.html:12)
  //}
}
