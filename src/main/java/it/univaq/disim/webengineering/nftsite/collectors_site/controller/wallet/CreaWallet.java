package it.univaq.disim.webengineering.nftsite.collectors_site.controller.wallet;

import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.security.Security;
import java.util.Arrays;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.web3j.crypto.ECDSASignature;
import org.web3j.crypto.Hash;
import org.web3j.crypto.Keys;
import org.web3j.crypto.Sign;
import org.web3j.crypto.Sign.SignatureData;
import org.web3j.utils.Numeric;

import it.univaq.disim.webengineering.nftsite.collectors_site.controller.CollectorsBaseController;
import it.univaq.disim.webengineering.nftsite.collectors_site.controller.Utility;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.DAO.WalletDAO;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.DAOimp.CollectorsDataLayer;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.User;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.Wallet;
import it.univaq.disim.webengineering.nftsite.framework.data.DataException;
import it.univaq.disim.webengineering.nftsite.framework.result.TemplateManagerException;
import it.univaq.disim.webengineering.nftsite.framework.result.TemplateResult;
import it.univaq.disim.webengineering.nftsite.framework.security.SecurityHelpers;

public class CreaWallet extends CollectorsBaseController {
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException
     * @throws DataException
     * @throws IOException
     */
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, DataException, IOException {
        if (request.getMethod().equals("POST")) {
            action_crea(request, response);
        } else {
            try {
                HttpSession s = SecurityHelpers.checkSession(request);
                String https_redirect_url = SecurityHelpers.checkHttps(request);
                request.setAttribute("https-redirect", https_redirect_url);
                if (s == null) {
                    action_notLogged(request, response);
                } else {
                    User user = Utility.getUser(request);
                    if (user != null) {
                        request.setAttribute("user", user);
                    }
                    action_default(request, response);
                }
            } catch (TemplateManagerException | DataException | IOException ex) {
                handleError(ex, request, response);
            }
        }
    }


    private void action_default(HttpServletRequest request, HttpServletResponse response) throws IOException, TemplateManagerException, DataException {
        TemplateResult result = new TemplateResult(getServletContext());
        request.setAttribute("referrer", request.getParameter("referrer"));
        HttpSession s = request.getSession(false);

        String message = generateRandomString(10);
        s.setAttribute("random_msg", message);

        request.setAttribute("random_msg", message);
        request.setAttribute("result", "");
        result.activate("wallet/add_wallet.ftl", request, response);
    }



    private void action_crea(HttpServletRequest request, HttpServletResponse response) {

        try {
            TemplateResult result = new TemplateResult(getServletContext());
            request.setAttribute("referrer", request.getParameter("referrer"));
            HttpSession s = request.getSession(false);
            if (s == null || s.getAttribute("random_msg") == null) {
                throw new DataException("Non è stato possibile ottenere i dati di sessione.");
            }
            String message = (String) s.getAttribute("random_msg");
            String pk = request.getParameter("pk");
            String signature = request.getParameter("signature");

            boolean validity = verifyEthereumSignature(pk, message, signature);

            if (validity) {

                User user = Utility.getUser(request);

                CollectorsDataLayer dataLayer = ((CollectorsDataLayer) request.getAttribute("datalayer"));
                WalletDAO WalletDAO = dataLayer.getWalletDAO();
                Wallet wallet = WalletDAO.createWallet();

                wallet.setAddress(pk);
                wallet.setUserId(user.getKey());
                WalletDAO.storeWallet(wallet);
                WalletDAO.saveNfts(wallet);

                request.setAttribute("nfts_addes", wallet.getNfts().size());
                request.setAttribute("result", "valid");
            }
            else{
                String new_message = generateRandomString(10);
                s.setAttribute("random_msg", new_message);
                request.setAttribute("random_msg", new_message);
                request.setAttribute("result", "invalid");
            }

            request.setAttribute("address", pk);
            result.activate("wallet/add_wallet.ftl", request, response);
        } catch (DataException | TemplateManagerException e) {
            handleError(e, request, response);
        }
    }

    private void action_notLogged(HttpServletRequest request, HttpServletResponse response) throws IOException, TemplateManagerException, ServletException {
        accessCheckFailed(request, response);
    }

        /**
     * Verify the validity of a given Ethereum signature.
     * 
     * @param publicKey The Ethereum public key as a string.
     * @param message The message to be verified.
     * @param signature The signature of the message.
     * @return True if the signature is valid, false otherwise.
     *
     * @throws DataExeption
     */
    private static boolean verifyEthereumSignature(String address, String message, String signature) throws DataException {

        // Add BouncyCastleProvider as a security provider.
        Security.addProvider(new BouncyCastleProvider());

        address = address.toLowerCase();

        try {
            final String personalMessagePrefix = "\u0019Ethereum Signed Message:\n";
            boolean match = false;

            final String prefix = personalMessagePrefix + message.length();
            final byte[] msgHash = Hash.sha3((prefix + message).getBytes());
            final byte[] signatureBytes = Numeric.hexStringToByteArray(signature);
            byte v = signatureBytes[64];
            if (v < 27) {
                v += 27;
            }

            final SignatureData sd = new SignatureData(v,
                    Arrays.copyOfRange(signatureBytes, 0, 32),
                    Arrays.copyOfRange(signatureBytes, 32, 64));

            String addressRecovered;

            // Iterate for each possible key to recover
            for (int i = 0; i < 4; i++) {
                final BigInteger publicKey = Sign.recoverFromSignature((byte) i, new ECDSASignature(
                                new BigInteger(1, sd.getR()),
                                new BigInteger(1, sd.getS())), msgHash);

                if (publicKey != null) {
                    addressRecovered = "0x" + Keys.getAddress(publicKey).toLowerCase();

                    if (addressRecovered.equals(address)) {
                        match = true;
                        break;
                    }
                }
            }

            return match;
        }
        catch (Exception e) {
            throw new DataException(e);
        }
    }


    private static String generateRandomString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new SecureRandom();
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(characters.length());
            char randomChar = characters.charAt(randomIndex);
            sb.append(randomChar);
        }

        return sb.toString();
    }
}