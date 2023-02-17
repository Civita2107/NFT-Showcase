<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    
    <!--TAILWIND-->
    <script src="https://cdn.tailwindcss.com"></script>
    <!--STYLE-->
    <link rel="stylesheet" href="${assets}/style.css">

    <link rel="icon" href="${assets}/favicon.ico">
    <title>NFT Showcase</title>
</head>
<body>
    
<#include "outline_navbar.ftl">

<div class="w-full h-screen p-2 min-h-fit">
    <#include content_tpl>
</div>

<#include "outline_footer.ftl">

</body>
</html>