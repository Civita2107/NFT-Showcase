<div class="grid grid-flow-row grid-cols-1 md:grid-cols-2 lg:grid-cols-4">
    <#list nfts as nft>
        <a href="visualizza-commenti?id=${nft.getKey()?c}" class="flex flex-col m-2 border rounded-lg shadow-lg">
            <img class="object-contain bg-gray-300 aspect-square" src="${nft.getMetadata()}" alt="${nft.getTitle()}">
            <h2 class="px-4 py-2 text-lg font-bold">${nft.getTitle()}</h2>
        </a>
    </#list>
</div>