        <div class="container mx-auto px-6">
            <div class="md:flex md:items-center">
                <!--foto nft-->
                <div class="w-full h-64 md:w-1/2 lg:h-96">
                    <img class="h-full w-full rounded-md object-cover max-w-lg mx-auto" src="${nft.getMetadata()}" alt="${nft.getTitle()}" onerror="this.src='${assets}/fallback.png'">
                </div>
                <!-- descrizione nft -->
                <div class="w-full max-w-lg mx-auto mt-5 md:ml-8 md:mt-0 md:w-1/2">
                    <h3 class="text-gray-700  text-lg">
                        ${nft.getTitle()}
                    </h3>
                    <span class="text-gray-500 mt-3">owned by ${user.getUsername()}
                    </span>
                    <hr class="my-3">
                    <span class="text-gray-500  mt-3">Descrizione <br>
                        ${nft.getDescription()}
                    </span>
                </div>
            </div>
            <div class="mt-16">
                <h3 class="text-gray-600 text-2xl font-medium">Altri Nft dell'utente</h3>
                <div class="grid gap-6 grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 mt-6">
                    <#list nfts[0..3]
                        as nf>
                        <div class="w-full max-w-sm mx-auto rounded-md shadow-md overflow-hidden">
                            <a href="visualizza-nft?id=${nf.getKey()?c}">
                                <img class="flex items-end justify-end h-56 w-full bg-cover" src="${nf.getMetadata()}" alt="${nft.getTitle()}" onerror="this.src='${assets}/fallback.png'">
                            </a>
                            <div class="px-5 py-3">
                                <h3 class="text-gray-700">
                                    ${nf.getTitle()}
                                </h3>
                            </div>
                        </div>
                    </#list>
                </div>
            </div>
            <!-- comment form -->
            <div class="flex mx-auto items-center justify-center shadow-lg mt-56  mb-4 max-w-lg">
                <form method="post" class="w-full max-w-xl bg-white rounded-lg px-4 pt-2" action="crea-commento?nft_id=${nft.getKey()?c}">
                    <div class="flex flex-wrap -mx-3 mb-6">
                        <h2 class="px-4 pt-3 pb-2 text-gray-800 text-lg">Scrivi una commento</h2>
                        <div class="w-full md:w-full px-3 mb-2 mt-2">
                            <textarea class="bg-gray-100 rounded border border-gray-400 leading-normal resize-none w-full h-20 py-2 px-3 font-medium placeholder-gray-700 focus:outline-none focus:bg-white" name="text" value="Scrivi qui..." required></textarea>
                        </div>
                        <div class="w-full md:w-full flex items-start  px-3">
                            <div class="flex items-start w-1/2 text-gray-700 px-2 mr-auto">
                            </div>
                            <div class="-mr-1">
                                <input type="submit" name="pubblica" class="bg-white text-gray-700 font-medium py-1 px-4 border border-gray-400 rounded-lg tracking-wide mr-1 hover:bg-gray-100" value="pubblica">
                            </div>
                        </div>
                    </div>
                </form>
            </div>
            <!-- commenti -->
            <#list user_comment as uc>
                <div class="relative grid grid-cols-1 gap-4 p-4 mb-8 border rounded-lg bg-white shadow-lg">
                    <div class="relative flex gap-4">
                        <#if uc.getUser().getFoto()??>
                            <img class="relative rounded-lg -top-4 -mb-4   h-20 w-20" src="${uc.getUser().getFoto()}" alt="" loading="lazy">
                            <#else />
                            <img class="relative rounded-lg -top-4 -mb-4   h-20 w-20" src="${assets}/account.png" alt="" loading="lazy">
                        </#if>
                        <br>
                        <div class="flex flex-col w-full">
                            <div class="flex flex-row justify-between">
                                <p class="relative text-xl whitespace-nowrap truncate overflow-hidden">
                                    ${uc.getUser().getUsername()}
                                </p>
                                <a class="text-gray-500 text-xl" href="#"><i class="fa-solid fa-trash"></i></a>
                            </div>
                        </div>
                    </div>
                    <p class="-mt-4 text-gray-500">
                        ${uc.getText()}
                    </p>
                </div>
            </#list>
        </div>