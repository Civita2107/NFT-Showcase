<div class="relative flex flex-col justify-center">
    <form method="post" action="modifica-utente" class="w-full px-8 mx-auto mt-10 mb-24 lg:w-96" enctype="multipart/form-data">
        <h1 class="text-2xl font-bold text-center underline uppercase underline-offset-4">Modifica</h1>
       <#if user.getFoto()??>
            <img class="block object-cover p-4 mx-auto w-36 h-36 rounded-3xl" src="${user.getFotoAsDataURI()}" alt="${user.getUsername()}">
        <#else/>
            <img class="block object-cover p-4 mx-auto w-36 h-36 rounded-3xl" src="${assets}/account.png" alt="${user.getUsername()}">
        </#if>
        <input class="block w-full px-4 py-2 my-1 text-base border border-b-2 border-gray-500 rounded-md border-b-black" type="file" name="foto" id="foto">

        <label class="block w-full mt-4 text-lg font-semibold" for="username">Username</label>
        <input id="username" class="block w-full px-4 py-2 my-1 text-base border border-b-2 border-gray-500 rounded-md border-b-black" type="text" name="username" value="${user.getUsername()}">
        <label class="block w-full mt-4 text-lg font-semibold" for="email">Email</label>
        <input id="email" class="block w-full px-4 py-2 my-1 text-base border border-b-2 border-gray-500 rounded-md border-b-black" type="email" name="email" value="${user.getEmail()}">
        <label class="block w-full mt-4 text-lg font-semibold" for="password">Password attuale</label>
        <input id="password" class="block w-full px-4 py-2 my-1 text-base border border-b-2 border-gray-500 rounded-md border-b-black" type="password" name="password">
        <label class="block w-full mt-4 text-lg font-semibold" for="nuovaPassword">Nuova password</label>
        <input id="nuovaPassword" class="block w-full px-4 py-2 my-1 text-base border border-b-2 border-gray-500 rounded-md border-b-black" type="password" name="nuovaPassword">
        <#if error??>
            <p class="m-3 text-red-700">${error!"Errore sconosciuto"}</p>
        </#if>
        <button class="block px-8 py-2 mx-auto mt-8 mb-4 text-xl font-bold text-white uppercase bg-gray-900 rounded-xl" type="submit">Modifica</button>
        <a href="visualizza-utente?id=${logininfo.userid}" class="block px-8 py-2 mx-auto my-4 text-xl font-bold text-white uppercase bg-gray-500 rounded-xl w-fit">Annulla</a>
    </form>
</div>