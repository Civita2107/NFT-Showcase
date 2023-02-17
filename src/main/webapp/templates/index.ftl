<div class="grid grid-flow-row grid-cols-1 md:grid-cols-2 lg:grid-cols-4">
    <#list nfts as nft>
        <div class="flex flex-col m-2 border rounded-lg shadow-lg">
            <img src="${nft.getMetadata()}" alt="${nft.getTitle()}">
            <h2>${nft.getTitle()}</h2>
        </div>
    </#list>
</div>