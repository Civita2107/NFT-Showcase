<div class="w-full p-4">
    <form class="max-w-full p-4 mx-auto border shadow-lg w-96 rounded-2xl" method="post" action="aggiungi-wallet" >
        <#if (result == "valid")>
            <h1 class="my-2 text-2xl font-bold text-center text-green-500">WALLET AGGIUNTO</h1>
            <p class="text-lg font-bold">Indirizzo del wallet:</p>
            <p class="block w-full p-2 px-4 py-2 my-1 overflow-x-scroll text-base bg-gray-300 border border-b-2 border-gray-500 rounded-md border-b-black">${address}</p>
            <p class="text-lg font-bold">NFTs aggiunti:</p>
            <p class="block w-full p-2 px-4 py-2 my-1 overflow-x-scroll text-base bg-gray-300 border border-b-2 border-gray-500 rounded-md border-b-black">${nfts_addes}</p>
            <a class="block px-4 py-2 mx-auto mt-4 font-bold text-white duration-200 bg-gray-600 border rounded-full hover:bg-black w-min whitespace-nowrap" href="visualizza-utente?id=${logininfo.userid}">Torna alla pagina personale</a>
        <#else/>
            <#if (result == "invalid")>
                <h1 class="my-2 text-2xl font-bold text-center text-red-500">AUTENTICAZIONE FALLITA, RIPROVARE</h1>
            </#if>
            <label class="text-lg font-bold" for="pk">Indirizzo del wallet</label>
            <input type="text" name="pk" id="pk" class="block w-full px-4 py-2 my-1 text-base border border-b-2 border-gray-500 rounded-md border-b-black" value="${address!""}">
            <p class="text-lg font-bold">CODICE:</p>
            <p class="block w-full p-2 px-4 py-2 my-1 overflow-x-scroll text-base bg-gray-300 border border-b-2 border-gray-500 rounded-md border-b-black">${random_msg}</p>
            <label class="text-lg font-bold" for="signature">Codice firmato dal tuo wallet</label>
            <input type="text" name="signature" id="signature" class="block w-full px-4 py-2 my-1 text-base border border-b-2 border-gray-500 rounded-md border-b-black">
            <button class="block px-4 py-2 mx-auto mt-4 font-bold text-white duration-200 bg-gray-600 border rounded-full hover:bg-black w-min whitespace-nowrap" type="submit">Verifica</button>
        </#if>
    </form>
</div>