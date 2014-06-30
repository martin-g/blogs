var a = "A",
    b = "B";

var functionA = function() {
    console.log("a=", a)  // intentionally missing semicolon to show JSHint warning
};

var functionB = function() {
    console.log("b=", b);
};

var functionX = function() {
    functionA();
    functionB();
};

functionX();
