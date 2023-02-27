<form method="post" action="ricerca">
<div class="w-full min-h-screen p-2 h-fit">
  <div class="relative w-full flex items-center">

    <div class="ml-4">
      <label for="filtro" class="sr-only">Filtra per</label>
      <select id="filtro" name="filtro" value="tutto" class="bg-transparent focus:outline-none w-auto">
        <option value="tutto">Tutto</option>
        <option value="utenti">Utenti</option>
        <option value="nft">NFT</option>
        <option value="collezioni">Collezioni</option>
      </select>
    </div>
    <input type="text" placeholder="Cerca qui" name="keyword" class="w-full py-2 pr-10 pl-2  rounded-md border-gray-300 focus:border-indigo-500 focus:ring-1 focus:ring-indigo-500 focus:outline-none">
    <button type="submit" class="absolute inset-y-0 right-0 px-4 py-2  font-bold text-white duration-200 bg-gray-600 border rounded-full hover:bg-black w-min whitespace-nowrap">Cerca</button>
  </div>
</form>



<br>

<div class="utenti" id="utenti">
<div class="grid grid-flow-row grid-cols-1 md:grid-cols-2 lg:grid-cols-4">
    <#list users as utente>
        <a href="visualizza-utente?id=${utente.getKey()?c}" class="flex flex-row m-2 border shadow-lg rounded-2xl">
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
</div>

<div class="nft" id="nft" >
<div class=" grid grid-flow-row grid-cols-1 md:grid-cols-2 lg:grid-cols-4">
<#list nfts as nft>
        <a href="visualizza-commenti?id=${nft.getKey()?c}" class="flex flex-col m-2 border shadow-lg rounded-2xl">
            <img class="object-cover w-24 h-24 p-4 rounded-3xl" src="${nft.getMetadata()}" alt="${nft.getTitle()}" onerror="this.src='${assets}/fallback.png'">
            <h2 class="flex flex-col p-2">${nft.getTitle()}</h2>
        </a>
    </#list>
  </div>
  </div>


</div>
</div> 

<!-- TODO collezioni -->
      


</div>