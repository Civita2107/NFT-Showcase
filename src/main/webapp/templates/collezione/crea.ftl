<div class="relative flex flex-col justify-center">
    <form method="post" action="crea-collezione">
        <div class="w-full px-8 mx-auto mt-10 mb-24 lg:w-96">
            <label class="block w-full mt-4 text-lg font-semibold" for="nome">Nome</label>
            <input id="nome" class="block w-full px-4 py-2 my-1 text-base border border-b-2 border-gray-500 rounded-md border-b-black" type="text" name="nome"  required>
        </div>
        <label class="block w-full mt-4 text-lg font-semibold" for="pubblica">Pubblica</label>
        <input id="pubblica" class="block w-full px-4 py-2 my-1 text-base border border-b-2 border-gray-500 rounded-md border-b-black" type="checkbox" name="pubblica" value="pubblica">
        <label class="block w-full mt-4 text-lg font-semibold" for="nfts">NFTs</label>
        <div class="nft" id="nft">
            <div class=" grid grid-flow-row grid-cols-1 md:grid-cols-2 lg:grid-cols-4">
                <#list nftsAll as nft>
                <#if !nft.getCollection()??>
                    <div class="flex flex-col m-2 border shadow-lg rounded-2xl">
                        <img class="object-cover w-24 h-24 p-4 rounded-3xl" src="${nft.getMetadata()}" alt="${nft.getTitle()}" onerror="this.src='${assets}/fallback.png'">
                        <h2 class="flex flex-col p-2">
                            ${nft.getTitle()}
                            <input type="checkbox" class="block w-full px-4 py-2 my-1 text-base border border-b-2 border-gray-500 rounded-md border-b-black" name="nfts" value="${nft.getKey()}">
                        </h2>
                    </div>
                    </#if>
                </#list>
            </div>
        </div>
        <#if error??>
            <p class="m-3 text-red-700">
                ${error!"Errore sconosciuto"}
            </p>
        </#if>
        <button class="block px-8 py-2 mx-auto mt-8 mb-4 text-xl font-bold text-white uppercase bg-gray-900 rounded-xl" type="submit">CREATE</button>
    </form>
</div>