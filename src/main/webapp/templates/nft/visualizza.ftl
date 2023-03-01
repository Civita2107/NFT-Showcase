        <div class="container px-6 mx-auto">
            <div class="md:flex md:items-center">
                <!--foto nft-->
                <div class="w-full h-64 md:w-1/2 lg:h-96">
                    <img class="object-cover w-full h-full max-w-lg mx-auto rounded-md" src="${nft.getMetadata()}" alt="${nft.getTitle()}" onerror="this.src='${assets}/fallback.png'">
                </div>
                <!-- descrizione nft -->
                <div class="w-full max-w-lg mx-auto mt-5 md:ml-8 md:mt-0 md:w-1/2">
                    <h3 class="text-lg text-gray-700">
                        ${nft.getTitle()}
                    </h3>
                    <span class="mt-3 text-gray-500">owned by ${user.getUsername()}
                    </span>
                    <hr class="my-3">
                    <span class="mt-3 text-gray-500">Descrizione <br>
                        ${nft.getDescription()}
                    </span>
                </div>
            </div>
            <div class="mt-16">
                <h3 class="text-2xl font-medium text-gray-600">Altri Nft dell'utente</h3>
                <div class="grid grid-cols-1 gap-6 mt-6 sm:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4">
                    <#list nfts[0..3]
                        as nf>
                        <div class="w-full max-w-sm mx-auto overflow-hidden rounded-md shadow-md">
                            <a href="visualizza-nft?id=${nf.getKey()?c}">
                                <img class="flex items-end justify-end w-full h-56 bg-cover" src="${nf.getMetadata()}" alt="${nft.getTitle()}" onerror="this.src='${assets}/fallback.png'">
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
            <#if logininfo??>

            <!-- comment form -->
            <div class="flex items-center justify-center max-w-lg mx-auto mt-56 mb-4 shadow-lg">
                <form method="post" class="w-full max-w-xl px-4 pt-2 bg-white rounded-lg" action="crea-commento?nft_id=${nft.getKey()?c}">
                    <div class="flex flex-wrap mb-6 -mx-3">
                        <h2 class="px-4 pt-3 pb-2 text-lg text-gray-800">Scrivi un commento</h2>
                        <div class="w-full px-3 mt-2 mb-2 md:w-full">
                            <textarea class="w-full h-20 px-3 py-2 font-medium leading-normal placeholder-gray-700 bg-gray-100 border border-gray-400 rounded resize-none focus:outline-none focus:bg-white" name="text" value="Scrivi qui..." required></textarea>
                        </div>
                        <div class="flex items-start w-full px-3 md:w-full">
                            <div class="flex items-start w-1/2 px-2 mr-auto text-gray-700">
                            </div>
                            <div class="-mr-1">
                                <input type="submit" name="pubblica" class="px-4 py-1 mr-1 font-medium tracking-wide text-gray-700 bg-white border border-gray-400 rounded-lg hover:bg-gray-100" value="pubblica">
                            </div>
                        </div>
                    </div>
                </form>
            </div>
            </#if>
            <!-- commenti -->
            <#list user_comment as uc>
                <div class="relative grid grid-cols-1 gap-4 p-4 mb-8 bg-white border rounded-lg shadow-lg">
                    <div class="relative flex gap-4 py-4">
                        <#if uc.getUser().getFotoAsDataURI()??>
                            <img class="relative h-16 rounded-lg square" src="${uc.getUser().getFotoAsDataURI()}" alt="" loading="lazy">
                        <#else />
                            <img class="relative h-16 rounded-lg square" src="${assets}/account.png" alt="" loading="lazy">
                        </#if>
                        <br>
                        <div class="flex flex-col w-full">
                            <div class="flex flex-row justify-between">
                                <p class="relative overflow-hidden text-xl truncate whitespace-nowrap">
                                    ${uc.getUser().getUsername()}
                                </p>
                                <a class="text-xl text-gray-500" href="#"><i class="fa-solid fa-trash"></i></a>
                            </div>
                        </div>
                    </div>
                    <p class="-mt-4 text-gray-500">
                        ${uc.getText()}
                    </p>
                </div>
            </#list>
        </div>