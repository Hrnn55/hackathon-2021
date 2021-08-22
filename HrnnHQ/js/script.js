function revealMessage() {
  console.log(document.getElementById("hiddenMessage").style.display);
  if (document.getElementById("hiddenMessage").style.display == "none") {
    document.getElementById("hiddenMessage").style.display = "block";
  } else {
    document.getElementById("hiddenMessage").style.display = "none";
  }
}

// function loadDoc() {
//   var xhttp = new XMLHttpRequest();

//   xhttp.onreadystatechange = function () {
//     if (this.readyState == 4 && this.status == 200) {
//       document.getElementById("demo").innerHTML = this.responseText;
//     }
//   };
//   xhttp.open("GET", "ajax_info.txt", true);
//   xhttp.send();
// }

function displayMaps() {
  document.getElementById("map1").src =
    "https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3023.3031671736885!2d-73.98475358420863!3d40.73335414424501!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x89c2599fa57874bf%3A0x747a17c9c9c3e88c!2sMount%20Sinai%20Beth%20Israel!5e0!3m2!1sen!2sus!4v1629635449273!5m2!1sen!2sus";
  document.getElementById("map2").src =
    "https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d12090.465793700449!2d-73.99441915664752!3d40.748464511894106!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x89c2590f88c18e83%3A0xdc22c9b4f6226c1c!2sNYU%20Medical%20Center!5e0!3m2!1sen!2sus!4v1629639748807!5m2!1sen!2sus";
  document.getElementById("map3").src =
    "https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d12077.928255651404!2d-73.9328509302246!3d40.81737400000001!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x89c2f5cfe06bb48f%3A0x3c8bd5d6ce32daa2!2sNYC%20Health%20%2B%20Hospitals!5e0!3m2!1sen!2sus!4v1629639802841!5m2!1sen!2sus";

  // connectToDb("");

  document.getElementById("maps").style.display = "block";
}

function connectToDb(address) {
  var xhr = new XMLHttpRequest();
  var params = "type=0&address=" + address;
  xhr.open("POST", "http://192.168.64.2/js/index.php", true);

  xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");

  xhr.onreadystatechange = function () {
    //Call a function when the state changes.

    if (xhr.readyState == 4) {
      alert(xhr.responseText);
    }
  };
  xhr.send(params);
  console.log("something happened.");
}
