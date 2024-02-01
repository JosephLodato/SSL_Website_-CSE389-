
<?php
if($argc>1){
    /* The output expected from HTML form is: name1=val1&name2=val2&name3=val3 
       Thus, argv[1] contains the list of everything entered, regardless of how many value are submitted.
       parse_srt expects this format and splits them into name -> value pairs, which I have specified
       to then be placed into the _POST superglobal variable
    */
    parse_str($argv[1], $_POST);
}

/* This is horrible, but it works. Seek to the end of the Comments .HTML file 
* (not .txt because I want styling), go back 15 characters, which is the length of
* </body></html>, write the custom message from there, then replace </body></html>
*/
$commentFile = fopen("media/RyanMedia/RyanComments.html", "r+");
fseek($commentFile, -15, SEEK_END);

$dateObj = new DateTime("now", new DateTimeZone('America/New_York'));
$dateStr = $dateObj->format('m/d/y H:i A');

fwrite($commentFile, "\n<div><p><b>".$dateStr.": "."</b>".$_POST["newComment"]."</p></div>");
//fwrite($commentFile,"");
fwrite($commentFile, "\n</body></html>");

//echo ftell($commentFile), "\n\n";

readfile("webpages/Ryan.html");
?>