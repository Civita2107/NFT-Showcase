<form method="post" action="ricerca">
  <div class="w-full min-h-screen p-2 h-fit">
    <div class="relative flex items-center w-full">
      <div class="ml-4">
        <label for="filtro" class="sr-only">Filtra per ${filtro}
        </label>
        <select id="filtro" name="filtro" class="w-auto bg-transparent focus:outline-none">
          <option value="tutto" ${(filtro == "tutto")?string("selected", "")}>Tutto</option>
          <option value="utenti" ${(filtro == "utenti")?string("selected", "")}>Utenti</option>
          <option value="nft" ${(filtro == "nft")?string("selected", "")}>NFT</option>
          <option value="collezioni" ${(filtro == "collezioni")?string("selected", "")}>Collezioni</option>
        </select>
      </div>
      <input type="text" placeholder="Cerca qui" name="keyword" value="${keyword}" class="w-full py-2 pl-2 pr-10 border-gray-300 rounded-md focus:border-indigo-500 focus:ring-1 focus:ring-indigo-500 focus:outline-none">
      <button type="submit" class="absolute inset-y-0 right-0 px-4 py-2 font-bold text-white duration-200 bg-gray-600 border rounded-full hover:bg-black w-min whitespace-nowrap">Cerca</button>
    </div>
</form>
<br>
<#if (filtro=="tutto" || filtro=="utenti" )>
  <div class="utenti" id="utenti">
    <div class="grid grid-flow-row grid-cols-1 md:grid-cols-2 lg:grid-cols-4">
      <#list users as utente>
        <a href="visualizza-utente?id=${utente.getKey()?c}" class="flex flex-row m-2 border shadow-lg rounded-2xl">
          <#if utente.getFotoAsDataURI()??>
            <img class="object-cover w-24 h-24 p-4 rounded-3xl" src="${utente.getFotoAsDataURI()}" alt="${utente.getUsername()}">
            <#else />
            <img class="object-cover w-24 h-24 p-4 rounded-3xl" src="${assets}/account.png" alt="${utente.getUsername()}">
          </#if>
          <div class="flex flex-col p-2">
            <h2>
              ${utente.getUsername()}
            </h2>
            <h2>Seguiti: ${utente.getFollower()?size!"error"}
            </h2>
            <h2>Segue: ${utente.getFollowing()?size!"error"}
            </h2>
          </div>
        </a>
      </#list>
    </div>
  </div>
</#if>
<#if (filtro=="tutto" || filtro=="nft" )>
  <div class="nft" id="nft">
    <div class="grid grid-flow-row grid-cols-1 md:grid-cols-2 lg:grid-cols-4">
      <#list nfts as nft>
        <a href="visualizza-nft?id=${nft.getKey()?c}" class="flex flex-col m-2 border shadow-lg rounded-2xl">
          <img class="object-cover w-24 h-24 p-4 rounded-3xl" src="${nft.getMetadata()}" alt="${nft.getTitle()}" onerror="this.src='${assets}/fallback.png'">
          <h2 class="flex flex-col p-2">
            ${nft.getTitle()}
          </h2>
        </a>
      </#list>
    </div>
  </div>
</#if>

<#if (filtro=="tutto" || filtro=="collezioni")>
  <div class="collezioni" id="collezioni">
    <div class="grid grid-flow-row grid-cols-1 md:grid-cols-2 lg:grid-cols-4">
      <#list collections as collection>
        <a href="visualizza-collezione?id=${collection.getKey()?c}" class="flex flex-col m-2 border shadow-lg rounded-2xl">
          <h2 class="flex flex-col p-2">
            ${collection.getNome()}
          </h2>
        </a>
      </#list>
    </div>
  </div>
</#if>