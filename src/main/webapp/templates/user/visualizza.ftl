<div class="flex flex-row p-4 border border-l-0 border-r-0 shadow-md">
    <#if user.getFoto()??>
        <img class="object-cover p-4 w-36 h-36 rounded-3xl" src="user.getFoto()" alt="${user.getUsername()}">
    <#else/>
        <img class="object-cover p-4 w-36 h-36 rounded-3xl" src="${assets}/account.png" alt="${user.getUsername()}">
    </#if>
    <div class="flex flex-col w-full">
        <div class="flex flex-col justify-between w-full p-2 pb-0 mr-4 lg:flex-row">
            <h1 class="font-bold">${user.getUsername()}</h1>
            <h2>Seguiti: ${user.getFollower()?size!"error"}</h2>
            <h2>Segue: ${user.getFollowing()?size!"error"}</h2>
        </div>
        <a class="p-2" href="mailto:${user.getEmail()}">${user.getEmail()}</a>
        <#if logininfo?? && (user.getKey() == logininfo.userid)>
            <a class="px-4 py-2 ml-2 mr-auto font-bold text-white duration-200 bg-gray-600 border rounded-full hover:bg-black w-min whitespace-nowrap" href="modifica-utente">modifica</a>
        </#if>
    </div>
</div>
<div class="w-full p-5 border-b shadow-md h-fit" style="min-height: 6rem;">
    <#if logininfo?? && (user.getKey() == logininfo.userid)>
        <a class="float-right px-4 py-2 ml-auto mr-2 font-bold text-white duration-200 bg-gray-600 border rounded-full hover:bg-black w-min whitespace-nowrap" href="crea-collezione">+ collezione</a>
    </#if>
    <div class="grid grid-flow-row grid-cols-1 md:grid-cols-2 lg:grid-cols-4">
        <#list collezioni as collezione>
            <a href="visualizza-collezione?id=${collezione.getKey()?c}" class="flex flex-col p-2 m-2 border rounded-lg shadow-lg">
                <h1 class="font-bold">${collezione.getNome()}</h1>
                <h2>Nfts: ${collezione.getNfts()?size}</h2>
            </a>
        </#list>
    </div>
</div>
<div class="w-full p-5 border-b shadow-md h-fit" style="min-height: 6rem;">
    <#if logininfo?? && (user.getKey() == logininfo.userid)>
        <a class="float-right px-4 py-2 ml-auto mr-2 font-bold text-white duration-200 bg-gray-600 border rounded-full hover:bg-black w-min whitespace-nowrap" href="aggiungi-wallet">+ wallet</a>
    </#if>
    <div class="grid grid-flow-row grid-cols-1 md:grid-cols-2 lg:grid-cols-4">
        <#list nfts as nft>
            <a href="visualizza-commenti?id=${nft.getKey()?c}" class="flex flex-col m-2 border rounded-lg shadow-lg">
                <img class="object-contain bg-gray-300 aspect-square" src="${nft.getMetadata()}" alt="${nft.getTitle()}">
                <h2 class="px-4 py-2 text-lg font-bold">${nft.getTitle()}</h2>
            </a>
        </#list>
    </div>
</div>