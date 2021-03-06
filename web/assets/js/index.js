var socket = false;
var url = "ws://localhost:8080/Breakout/index";

var backToStartScreen = function () {
    hidebackbutton();
    hideEverything();
    $("main").css("margin-top","100px");
    $("footer").show();
    $("#mainMenu").show();
};

var enterNameSingleplayer = function(){
    hideEverything();
    displaybackbutton();
    $("#nameForm").show();
};

var enterNameMultiplayer = function(){  
    hideEverything();
    displaybackbutton();    
    $("#nameFormMultiplayer").show();
};

var chooseGameType = function(e){
    e.preventDefault(); 
    hideEverything();
    displaybackbutton();
    $("#gameType").show();
};


var chooseDiffuculty = function (e){
    e.preventDefault();
    hideEverything();  
    var name = $("#playerName").val();
    console.log("player"+name);
    var nameObject = {
        name1: name,
        type: "sp"
    };
    storeName(nameObject);    
    socket.send("player "+name); 
    $("#gameDifficulty").show();     
};

var showGameInfo = function (){
    hideEverything();
    displaybackbutton();
    $('#gameInfo').show();
    $("footer").hide();
    $("main").css("margin-top","10px");
};

var storeName = function (nameObject) {
    if (typeof Storage !== void(0)) {
        localStorage.setItem("name", JSON.stringify(nameObject));
    } else {
        alert("Try another browser to play BreakWorld");
    }
};

var chooseMultiPlayerDiffuculty = function(e){
    e.preventDefault();
    hideEverything();
    var name1 = $("#playerNameMultiplayer1").val();
    var name2 = $("#playerNameMultiplayer2").val();
    var nameObject = {
        name1: name1,
        name2: name2,
        type: "mp"
    };
    storeName(nameObject); 
    socket.send("player "+name1);
    socket.send("player "+name2);
    $("#gameDifficulty").show(); 
};

var storeName = function (nameObject) {
    if (typeof Storage !== void(0)) {
        localStorage.clear();
        localStorage.setItem("player", JSON.stringify(nameObject));  
    } else {
        alert("Try another browser to play BreakWorld");
    }
};


var chooseMultiPlayerType = function () {
    hideEverything();
    $("#showMultiplayer").show();
};

var startGame = function () {
    window.location.replace("game.html");
};

var showHighscores = function () {
    hideEverything();
    displaybackbutton();  
    socket.send("highscores");
    socket.onmessage = function(evt){
        var html1 = "<table>";
        var html2 = "<table>";       
        html1 += "<tr> <th>Rank</th> <th>Singleplayer</th> <th>Score</th> </tr>";
        html2 += "<tr> <th>Rank</th> <th>Multiplayer</th>  <th>Score</th> </tr>";
        var tabelData = JSON.parse(evt.data);       
        for(var x = 0; x < tabelData.length; x++){
            html1 += "<tr><td>"+(x+1)+"</td><td>"+tabelData[x].p+"</td><td>"+tabelData[x].s+"</td></tr>";
        }
        for(var x = 0; x < tabelData.length; x++){
            html2 += "<tr><td>"+(x+1)+"</td><td>"+tabelData[x].p+"</td><td>"+tabelData[x].m+"</td></tr>";
        }
        html1 += "</table>";
        html2 += "</table>";       
        $("#showHighscores").append(html1);
        $("#showHighscores").append(html2);
    };   
    $("#showHighscores").show();
};

var displaybackbutton = function(){
    $("#back").show();
};

var hidebackbutton = function(){
    $("#back").hide();
};

var hideEverything = function(){
    $("#mainMenu").hide();
    $("#nameForm").hide();
    $("#nameFormMultiplayer").hide();
    $("#gameType").hide();
    $("#gameDifficulty").hide();
    $("#showHighscores").hide();
    $('#gameInfo').hide();
};
    
$( document ).ready(function() {
    socket = new WebSocket(url);
    socket.onopen = function(){};
    socket.onmessage = function(){};
    
    hideEverything();
    hidebackbutton();
    
    $("#mainMenu").show();   
    $("#back").on("click",backToStartScreen);  
    $("#startGame").on("click",chooseGameType);
    $("#highscores").on("click",showHighscores);
    $('#about').on('click',showGameInfo);    
    $("#singlePlayer").on("click", enterNameSingleplayer);
    $("#multiPlayer").on("click", enterNameMultiplayer);  
    $("#playSingleplayer").on('click',chooseDiffuculty);
    $("#playMultiplayer").on('click',chooseMultiPlayerDiffuculty);   
    $("#easy, #medium, #hard").on("click",startGame);   
});
