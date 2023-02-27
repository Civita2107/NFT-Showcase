<div class="w-full p-5 border-b shadow-md h-fit" style="min-height: 6rem;">
    <#if logininfo?? && (user.getKey() == logininfo.userid)>
    <a class="px-4 py-2 ml-2 mr-auto font-bold text-white duration-200 bg-gray-600 border rounded-full hover:bg-black w-min whitespace-nowrap" href="modifica-collezione">modifica collezione</a>
    </#if>
    <div class="grid grid-flow-row grid-cols-1 md:grid-cols-2 lg:grid-cols-4">
        <#list nfts as nft>
            <a href="visualizza-nft?id=${nft.getKey()?c}" class="flex flex-col m-2 border shadow-lg rounded-2xl">
                <img class="object-contain bg-gray-300 aspect-square rounded-t-2xl" src="${nft.getMetadata()}" alt="${nft.getTitle()}" onerror="this.src='${assets}/fallback.png'">
                <h2 class="px-4 py-2 text-lg font-bold">${nft.getTitle()}</h2>
            </a>
        </#list>
    </div>
</div>