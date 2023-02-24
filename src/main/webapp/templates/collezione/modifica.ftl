<div class="relative flex flex-col justify-center">
    <form method="post" action="modifica-utente" class="w-full px-8 mx-auto mt-10 mb-24 lg:w-96">
        <label class="block w-full mt-4 text-lg font-semibold" for="id">Id</label>
        <input id="id" class="block w-full px-4 py-2 my-1 text-base border border-b-2 border-gray-500 rounded-md border-b-black" type="number" name="id">
        <label class="block w-full mt-4 text-lg font-semibold" for="nome">Nome</label>
        <input id="nome" class="block w-full px-4 py-2 my-1 text-base border border-b-2 border-gray-500 rounded-md border-b-black" type="text" name="nome">
        <label class="block w-full mt-4 text-lg font-semibold" for="pubblica">Pubblica</label>
        <input id="pubblica" class="block w-full px-4 py-2 my-1 text-base border border-b-2 border-gray-500 rounded-md border-b-black" type="checkbox" name="pubblica">
        <label class="block w-full mt-4 text-lg font-semibold" for="nfts">NFTs</label>
        <select name="nfts" id="nfts" multiple>
            <#list nftsAll as nft>
                <option value="${nft.getKey()}">${nft.getKey()}</option>
            </#list>
        </select>
        <#if error??>
            <p class="m-3 text-red-700">${error!"Errore sconosciuto"}</p>
        </#if>
        <button class="block px-8 py-2 mx-auto mt-8 mb-4 text-xl font-bold text-white uppercase bg-gray-900 rounded-xl" type="submit">SUBMIT</button>
    </form>
</div>