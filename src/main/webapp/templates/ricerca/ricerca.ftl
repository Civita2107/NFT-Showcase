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

<div class="flex flex-col items-center justify-between px-4 py-2 overflow-x-scroll md:flex-row">
    <div class="flex flex-row justify-start mr-4">
        <a href="home" class="mx-2">Home</a>
        <a href="lista-utenti" class="mx-2">Utenti</a>
        <a href="ricerca" class="mx-2">Ricerca</a>
    </div>
    <div class="flex flex-row justify-end">
        <a class="mx-2 font-bold" href="login?referrer=${referrer}">Login</a>
        <a class="mx-2 font-bold" href="registration?referrer=${referrer}">Registrazione</a>
    </div>
</div>

<div class="w-full min-h-screen p-2 h-fit">




<!------------------------------------------------------------->
<!------------------------------------------------------------->
<!------------------------------------------------------------->



<#list users as u>
    <p>u ${u.getEmail()}</p>
</#list>

<#list collections as c>
    <p>c ${c.getNome()}</p>
</#list>

<#list nfts as n>
    <p>n ${n.getKey()}</p>
</#list>

<#list wallets as w>
    <p>w ${w.getKey()}</p>
</#list>


<!------------------------------------------------------------->
<!------------------------------------------------------------->
<!------------------------------------------------------------->

</div>

<div class="flex flex-row justify-center">
    <b>&copy; Copyright 2023 by <a href="home">NFT Showcase</a></b>
</div>

</body>
</html>