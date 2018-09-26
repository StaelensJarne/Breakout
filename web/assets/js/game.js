var socket = false;

var canvas = document.getElementById("canvasBreakout");
if (canvas.getContext) {
    var ctx = canvas.getContext("2d");
}
else {
    alert("Your browser does not support canvas. Try another.");
}

var level = 1;

var life = new Image();
life.src = 'images/game/favorite-heart-button.png';
//ctx.rect(palletX, midHeight, palletWidth, rectangleHeight);
var midWidth = canvas.width / 2;
var midHeight = canvas.height - 80;
var palletWidth = 150;
var rectangleHeight = 20;
var widthGroupRectangles = canvas.width - 100;
var startingPoint = 50;
var endingPoint = 1050;
var palletX = (canvas.width - palletWidth) / 2;
var radius = 10;
var ballX = canvas.width / 2;
var ballY = midHeight - (rectangleHeight / 2);
var travelSpeedX = 5;
var travelSpeedY = -5;
var lives = 3;
var score = 0;
var space = false;
var left = false;
var right = false;
var playerName = "";
var SinglePlayerScore = 0;
var totalBlocks;
var generatedBlocks=[];

var impureBlocks = [];
var generatedBlocks = [];

var keys = {
    "space": false,
    "left": false,
    "right": false
};
//hier worden de attributen aangevuld vanuit sockets
var ball={};
var pallet={};

document.addEventListener("keydown", move, false);
document.addEventListener("keyup", stop, false);

function detectCollision() {
    for (var row = 0; row < generatedBlocks.length; row++) {
        for (var column = 0; column < generatedBlocks[row].length; column++) {
            var possibleCollidedBlock = generatedBlocks[row][column];
            var xCoord = possibleCollidedBlock.xCoordination;
            var yCoord = possibleCollidedBlock.yCoordination;

            if (possibleCollidedBlock.shown) {
                if ((ballX > xCoord && ballX < xCoord + possibleCollidedBlock.length &&
                        ballY > yCoord && ballY < yCoord + rectangleHeight + radius) || (ballX > xCoord && ballX < xCoord + possibleCollidedBlock.length &&
                        ballY < yCoord && ballY > yCoord - rectangleHeight + radius)) {
                    travelSpeedY = -travelSpeedY;
                    possibleCollidedBlock.shown = false;
                    score += possibleCollidedBlock.score;
                }
                if ((ballX === xCoord - radius || ballX === xCoord + possibleCollidedBlock.length + radius) && ballY > yCoord - rectangleHeight && ballY < yCoord + rectangleHeight) {
                    travelSpeedX = -travelSpeedX;
                    possibleCollidedBlock.shown = false;
                    score += possibleCollidedBlock.score;
                }
            }
        }
    }
}

function palletCollision() {
    if (ballX > palletX && ballX <= palletX + palletWidth / 8 && ballY > midHeight - (rectangleHeight / 2)) {
        travelSpeedX = -7;
        travelSpeedY = 3;
    }
    if (ballX > palletX + palletWidth / 8 && ballX <= palletX + palletWidth / 4 && ballY > midHeight - (rectangleHeight / 2)) {
        travelSpeedX = -6;
        travelSpeedY = 4;
    }
    if (ballX > palletX + palletWidth / 4 && ballX <= palletX + palletWidth * 3 / 8 && ballY > midHeight - (rectangleHeight / 2)) {
        travelSpeedX = -5;
        travelSpeedY = 5;
    }
    if (ballX > palletX + palletWidth * 3 / 8 && ballX <= palletX + palletWidth / 2 && ballY > midHeight - (rectangleHeight / 2)) {
        travelSpeedX = -3;
        travelSpeedY = 7;
    }
    if (ballX > palletX + palletWidth / 2 && ballX <= palletX + palletWidth * 5 / 8 && ballY > midHeight - (rectangleHeight / 2)) {
        travelSpeedX = 4;
        travelSpeedY = 6;
    }
    if (ballX > palletX + palletWidth * 5 / 8 && ballX <= palletX + palletWidth * 3 / 4 && ballY > midHeight - (rectangleHeight / 2)) {
        travelSpeedX = 5;
        travelSpeedY = 5;
    }
    if (ballX > palletX + palletWidth * 3 / 4 && ballX <= palletX + palletWidth * 7 / 8 && ballY > midHeight - (rectangleHeight / 2)) {
        travelSpeedX = 6;
        travelSpeedY = 4;
    }
    if (ballX > palletX + palletWidth * 7 / 8 && ballX <= palletX + palletWidth && ballY > midHeight - (rectangleHeight / 2)) {
        travelSpeedX = 7;
        travelSpeedY = 3;
    }
}

function drawBlocks() {
    //context.rect(x,y,width,height);

    var yCoordForNextBlock = 40;

    for (var row = 0; row < generatedBlocks.length; row++) {
        var xCoordForNextBlock = startingPoint;

        for (var eachBlock = 0; eachBlock < generatedBlocks.length * 2; eachBlock++) {
            var currentBlock = generatedBlocks[row][eachBlock];

            currentBlock.xCoordination = xCoordForNextBlock;
            currentBlock.yCoordination = yCoordForNextBlock;

            if (currentBlock.shown) {
                ctx.beginPath();
                ctx.rect(xCoordForNextBlock, yCoordForNextBlock, currentBlock.length, rectangleHeight);
                ctx.fillStyle = currentBlock.color;
                ctx.fill();
                ctx.strokeStyle = "black";
                ctx.stroke();
                ctx.closePath();
            }
            xCoordForNextBlock += currentBlock.length;
        }
        yCoordForNextBlock += rectangleHeight;
    }
}

function drawPallet() {
    //context.rect(x,y,width,height);
    ctx.beginPath();
    ctx.rect(palletX, midHeight, palletWidth, rectangleHeight);
    ctx.fillStyle = "#006666";
    ctx.fill();
    ctx.strokeStyle = "black";
    ctx.stroke();
    ctx.closePath();
}

function drawBall() {
    //context.arc(x,y,r,sAngle,eAngle,counterclockwise);
    ctx.beginPath();
    ctx.arc(ballX, ballY, radius, 0, 2 * Math.PI, false);
    ctx.fillStyle = "#006666";
    ctx.fill();
    ctx.strokeStyle = "black";
    ctx.stroke();
    ctx.closePath();
}

function drawLevel() {
    ctx.beginPath();
    ctx.font = "14px Comic Sans MS";
    ctx.fillStyle = "black";
    ctx.textAlign = "center";
    ctx.fillText("Stage 1 - Level " + level, canvas.width / 2, 25);
    ctx.closePath();
}

function drawScore() {
    ctx.beginPath();
    ctx.font = "bold 20px Comic Sans MS";
    ctx.fillStyle = "black";
    ctx.textAlign = "left";
    ctx.fillText(playerName, 18, canvas.height - 30);
    ctx.fillText(score, 1010, canvas.height - 30);
    ctx.closePath();
}

function drawLives() {
    // 	context.drawImage(img,x,y);
    //	context.drawImage(img,x,y,width,height);
    var x = 16;
    var y = canvas.height - 20;

    for (var maxLives = 0; maxLives < lives; maxLives++) {
        ctx.beginPath();
        ctx.drawImage(life, x, y, 15, 15);
        ctx.closePath();

        x += 20;
    }
}

function reset() {
    ballX = canvas.width / 2;
    ballY = midHeight - (rectangleHeight / 2);
    palletX = (canvas.width - palletWidth) / 2;
    palletCollision();
}

function draw() {

    ctx.clearRect(0, 0, canvas.width, canvas.height);
    drawBall();
    drawPallet();
    drawBlocks();
    drawLevel();
    drawScore();
    drawLives();
    detectCollision();
    palletCollision();

    if (ballX + travelSpeedX > canvas.width - radius || ballX + travelSpeedX < radius) {
        travelSpeedX = -travelSpeedX;
    }
    if (ballY < radius) {
        travelSpeedY = -travelSpeedY;
    }
    if (ballX > palletX && ballX < palletX + palletWidth) {
        if (ballY > midHeight - (rectangleHeight / 2)) {
            travelSpeedY = -travelSpeedY;
        }
    }
    if (ballY > canvas.height - radius - 60) {
        lives--;
        if (lives === 0) {
            $("canvas").hide();
            $("#lostLevelGeneral").show();
            $('.score').append(score);
            
            // alert("Game over! You lost the game!");


        }
        else {
            reset();
        }
    }

    if (keys.space) {
        ballX += travelSpeedX;
        ballY += travelSpeedY;

        if (keys.right && palletX < canvas.width - palletWidth) {
            palletX += 10;
        }
        if (keys.left && palletX > 0) {
            palletX -= 10;
        }
    }

    totalBlocks = 50;
    for (var row = 0; row < generatedBlocks.length; row++) {
        for (var eachBlock = 0; eachBlock < generatedBlocks[row].length; eachBlock++) {
            var block = generatedBlocks[row][eachBlock];

            if (block.shown === false) {
                totalBlocks--;
                if (totalBlocks === 0) {
                    // alert("Great, get ready to next level!");
                    $("#nextLevelGeneral").show();
                    $('.score').text(score);
                    $("#lostLevelGeneral").hide();
                    $("canvas").hide();
                    //document.location.reload();
                }
            }
        }
    }
    requestAnimationFrame(draw);
}

function retryGame() {
    document.location.reload();
}

function nextLevel() {
    level++;
    document.location.reload();
    //ctx.fillText("Stage 1 - Level 2", canvas.width / 2, 25);
}

function toMainMenu() {
    window.location.replace("http://localhost:8080/Breakout/index.html");
}

function move(e) {
    if (e.keyCode === 32) {
        keys.space = true;
    }
    if (e.keyCode === 37) {
        keys.left = true;
    }
    if (e.keyCode === 39) {
        keys.right = true;
    }
}

function stop(e) {
    if (e.keyCode === 37) {
        keys.left = false;
    }
    if (e.keyCode === 39) {
        keys.right = false;
    }
}

function mergeSubArrays(impureBlocks) {
    for (var eachSubArray = 0; eachSubArray < impureBlocks.length; eachSubArray += 2) {
        generatedBlocks.push(impureBlocks[eachSubArray].
                concat(impureBlocks[eachSubArray + 1]));
    }
    console.log(generatedBlocks);
}

function replaceBlocksByBlocksWithPowers(blocks) {
    for (var eachSubArray = 0; eachSubArray < impureBlocks.length; eachSubArray++) {
        for (var eachBlockObj = 0; eachBlockObj < impureBlocks[eachSubArray].length; eachBlockObj++) {
            if (impureBlocks[eachSubArray][eachBlockObj].color === blocks[eachBlockObj].color) {
                impureBlocks[eachSubArray][eachBlockObj] = blocks[eachSubArray];
            }
        }
    }
    mergeSubArrays(impureBlocks);
}


var setName = function () {
    if (typeof Storage !== void(0)) {
        var speler = JSON.parse(localStorage.getItem("player"));
        if (speler.type === "sp") {
            return speler.name1;
        } else {
            return speler.name1+" "+speler.name2;
        }
    } else {
        alert("No name given");
    }
};

function putPowersInEachBlocks(arrayOfBlocks) {
    impureBlocks = arrayOfBlocks;
    //console.log(impureBlocks);

    for (var eachSubArrayOfBlocks = 0; eachSubArrayOfBlocks < arrayOfBlocks.length; eachSubArrayOfBlocks++) {
        var randomBlock = arrayOfBlocks[eachSubArrayOfBlocks][Math.floor(Math.random() * 5)];
        console.log(randomBlock);
        console.log(JSON.stringify(randomBlock));
        socket.send(JSON.stringify(randomBlock));
    }
    socket.send("giveObjectsWithPowers");
}


$(document).ready(function () {
    socket = new WebSocket("ws://localhost:8080/Breakout/game");


    socket.onopen = function () {
        //socket.send("generateBlocks");
        //socket.send("generatePowerUpDowns");
        //socket.send("generateBoss 2");

    };

    socket.onmessage = function (evt) {
        var typeData = (evt.data).split("/")[0];
        var data = (evt.data).split("/")[1];

        switch (typeData) {
            case "generateBlocks":
                putPowersInEachBlocks(JSON.parse(data));
                break;
            case "BlocksWithPowers":
                var pureData = data.replace("null", "");
                //console.log(pureData);
                
                ArrayWithBlocksAndPowers = JSON.parse(pureData);
                console.log(ArrayWithBlocksAndPowers);
                replaceBlocksByBlocksWithPowers(ArrayWithBlocksAndPowers);
                break;
            default :
                console.log("unknow data");
                break;
        }
        draw();
        playerName = setName();
    };


    $(".popupBtnRetry").on('click', retryGame);
    $(".popupBtnNextlvl").on('click', nextLevel);
    $(".popupBtnMainMenu").on('click', toMainMenu);

});
