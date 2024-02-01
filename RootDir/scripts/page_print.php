
<html>
<body>

<h1>Your wish is my Command. Here is your POST:</h1>


Your input was: 
<strong>
<?php 

if($argc>1){
    /* The output expected from HTML form is: name1=val1&name2=val2&name3=val3 
       Thus, argv[1] contains the list of everything entered, regardless of how many value are submitted.
       parse_srt expects this format and splits them into name -> value pairs, which I have specified
       to then be placed into the _POST superglobal variable
    */
    parse_str($argv[1], $_POST);
}

echo $_POST["input"]; 

?>

</strong>
 <br>
You... really could've been more creative...

</body>
</html>