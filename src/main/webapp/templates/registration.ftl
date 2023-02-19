<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    
    <!--TAILWIND-->
    <script src="https://cdn.tailwindcss.com"></script>
    <!--STYLE-->
    <link rel="stylesheet" href="${assets}/style.css">

    <link rel="icon" href="${assets}/favicon.ico">
    <title>Registrazione</title>
</head>
<body>
    
    <div class="relative flex flex-col justify-center">
        <form method="post" action="registration" class="w-full px-8 mx-auto mt-32 lg:w-96">
            <#if referrer??>
                <input type="hidden" name="referrer" value="${referrer}">
            </#if>
            <h1 class="mb-10 text-center pacific text-7xl"><b class="block pl-12 text-5xl press-start -mb-7">NFT</b>Showcase</h1>
            <h1 class="text-2xl font-bold text-center underline uppercase underline-offset-4">Registrati</h1>
            <label class="block w-full mt-4 text-lg font-semibold" for="username">Username</label>
            <input id="username" class="block w-full px-4 py-2 my-1 text-base border border-b-2 border-gray-500 rounded-md border-b-black" type="text" name="username">
            <label class="block w-full mt-4 text-lg font-semibold" for="email">Email</label>
            <input id="email" class="block w-full px-4 py-2 my-1 text-base border border-b-2 border-gray-500 rounded-md border-b-black" type="email" name="email">
            <label class="block w-full mt-4 text-lg font-semibold" for="password">Password</label>
            <input id="password" class="block w-full px-4 py-2 my-1 text-base border border-b-2 border-gray-500 rounded-md border-b-black" type="password" name="password">
            <#if error??>
                <p class="m-3 text-red-700">${error!}</p>
            </#if>
            <button class="block px-8 py-2 mx-auto my-8 text-xl font-bold text-white uppercase bg-gray-900 rounded-xl" type="submit">signup</button>
            <small>Hai già un account?</small>
            <a class="underline underline-offset-4" href="./login">Accedi</a>
        </form>
    </div>

</body>
</html>