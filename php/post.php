<?php

$name = $_GET['username'] ;
$mood = $_GET['mood'] ;

$fp = fopen('moods.txt', 'a');
fwrite($fp, 'Name : ' . $name . "\n" . 'Mood : ' . $mood . "\n\n");
fclose($fp);

echo "Your current mood is $mood .";

?>