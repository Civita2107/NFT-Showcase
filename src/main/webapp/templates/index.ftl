<div class="grid grid-flow-row grid-cols-1 md:grid-cols-2 lg:grid-cols-4">
    <#list nfts as nft>
        <a href="visualizza-nft?id=${nft.getKey()?c}" class="flex flex-col m-2 border shadow-lg rounded-2xl">
            <img class="object-contain bg-gray-300 aspect-square rounded-t-2xl" src="${nft.getMetadata()}" alt="${nft.getTitle()}" onerror="this.src='${assets}/fallback.png'">
            <h2 class="px-4 py-2 text-lg font-bold">${nft.getTitle()}</h2>
        </a>
    </#list>
</div>
<a href="./" class="block mx-auto mt-6 mb-4 text-lg font-bold w-fit">Carica altri nft</a>