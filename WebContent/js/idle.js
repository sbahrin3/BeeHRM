//this minutes var is not actually minutes, it depends on window.setInterval

var timeOut = 900;  //900 seconds
var secondsCounter = 0;
var diff = 0;


document.onclick = function() {
    secondsCounter = 0;
};
document.onmousemove = function() {
    secondsCounter = 0;
};
document.onmouseover = function() {
    secondsCounter = 0;
};
document.onkeypress = function() {
    secondsCounter = 0;
};

//disabled
//window.setInterval(checkIdleTime, 1000);

function checkIdleTime() {
    secondsCounter++;
    if (secondsCounter >= timeOut) {
        secondsCounter = 0;
        gotoExpiredPage();
        
    }
    if ( secondsCounter % 60 == 0 ) {
    	showWarningMessage("Inactivity detected!");
    }
    diff = timeOut - secondsCounter;
    if ( diff < 3 ) {
    	showWarningMessage("Inactivity detected! Logging out soon...");
    }
}
