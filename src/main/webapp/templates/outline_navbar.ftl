<div class="flex flex-col items-center justify-between px-4 py-2 overflow-x-scroll md:flex-row">
    <div class="flex flex-row justify-start mr-4">
        <a href="home" class="mx-2">Home</a>
        <a href="lista-utenti" class="mx-2">Utenti</a>
        <a href="ricerca" class="mx-2">Ricerca</a>
    </div>
    <div class="flex flex-row justify-end">
        <#if logininfo??>
            <a href="visualizza-utente?id=${logininfo.userid}" class="mx-2 font-bold">${logininfo.username}</a>
            <a href="logout" class="mx-2 font-bold">Logout</a>
        <#else>
            <a class="mx-2 font-bold" href="login?referrer=${referrer}">Login</a>
            <a class="mx-2 font-bold" href="registration?referrer=${referrer}">Registrazione</a>
        </#if>
    </div>
</div>