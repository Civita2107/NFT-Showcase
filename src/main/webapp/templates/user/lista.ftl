<div class="grid grid-flow-row grid-cols-1 md:grid-cols-2 lg:grid-cols-4">
    <#list utenti as utente>
        <a href="visualizza-utente?id=${utente.getKey()?c}" class="flex flex-row m-2 border rounded-lg shadow-lg">
            <#if utente.getFoto()??>
                <img class="object-cover w-24 h-24 p-4 rounded-3xl" src="utente.getFoto()" alt="${utente.getUsername()}">
            <#else/>
                <img class="object-cover w-24 h-24 p-4 rounded-3xl" src="${assets}/account.png" alt="${utente.getUsername()}">
            </#if>
            <div class="flex flex-col p-2">
                <h2>${utente.getUsername()}</h2>
                <h2>Seguiti: ${utente.getFollower()?size!"error"}</h2>
                <h2>Segue: ${utente.getFollowing()?size!"error"}</h2>
            </div>
        </a>
    </#list>
</div>