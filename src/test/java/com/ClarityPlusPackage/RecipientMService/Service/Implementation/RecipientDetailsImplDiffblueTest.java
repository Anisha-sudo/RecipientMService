package com.ClarityPlusPackage.RecipientMService.Service.Implementation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ClarityPlusPackage.RecipientMService.DTO.RecipientDetailsDTO;
import com.ClarityPlusPackage.RecipientMService.Entity.LoginDetails;
import com.ClarityPlusPackage.RecipientMService.Entity.Recipient;
import com.ClarityPlusPackage.RecipientMService.Repository.LoginRepo;
import com.ClarityPlusPackage.RecipientMService.Repository.RecipientDetailsRepo;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {RecipientDetailsImpl.class})
@ExtendWith(SpringExtension.class)
class RecipientDetailsImplDiffblueTest {
    @MockBean
    private EmailConfig emailConfig;

    @MockBean
    private LoginRepo loginRepo;

    @Autowired
    private RecipientDetailsImpl recipientDetailsImpl;

    @MockBean
    private RecipientDetailsRepo recipientDetailsRepo;

    /**
     * Method under test:
     * {@link RecipientDetailsImpl#getEmailIDByInstituteID(String)}
     */
    @Test
    void testGetEmailIDByInstituteID() {
        doNothing().when(emailConfig).sendOTPMail(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
        when(recipientDetailsRepo.findPersonalEmailIDByInstituteID(Mockito.<String>any()))
                .thenReturn("jane.doe@example.org");
        when(recipientDetailsRepo.findRecipientByInstituteID(Mockito.<String>any())).thenReturn(new ArrayList<>());
        String actualEmailIDByInstituteID = recipientDetailsImpl.getEmailIDByInstituteID("Institute ID");
        verify(recipientDetailsRepo).findPersonalEmailIDByInstituteID(Mockito.<String>any());
        verify(recipientDetailsRepo).findRecipientByInstituteID(Mockito.<String>any());
        verify(emailConfig).sendOTPMail(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
        assertEquals("OTP to sent linked emailID successfully!", actualEmailIDByInstituteID);
    }

    /**
     * Method under test:
     * {@link RecipientDetailsImpl#getEmailIDByInstituteID(String)}
     */
    @Test
    void testGetEmailIDByInstituteID2() {
        doNothing().when(emailConfig).sendOTPMail(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

        Recipient recipient = new Recipient();
        recipient.setInstituteID("Inside Implementation");
        recipient.setOTP(100000);
        recipient.setOrderID("Inside Implementation");
        recipient.setPersonalEmailID("jane.doe@example.org");
        recipient.setReceived(true);
        recipient.setRecipientFirstName("Jane");
        recipient.setRecipientLastName("Doe");
        recipient.setRecipientPhoneNumber("6625550144");
        recipient.setRetailer("Inside Implementation");

        ArrayList<Recipient> recipientList = new ArrayList<>();
        recipientList.add(recipient);
        doNothing().when(recipientDetailsRepo).saveByInstituteID(anyInt(), Mockito.<String>any());
        when(recipientDetailsRepo.findPersonalEmailIDByInstituteID(Mockito.<String>any()))
                .thenReturn("jane.doe@example.org");
        when(recipientDetailsRepo.findRecipientByInstituteID(Mockito.<String>any())).thenReturn(recipientList);
        String actualEmailIDByInstituteID = recipientDetailsImpl.getEmailIDByInstituteID("Institute ID");
        verify(recipientDetailsRepo).findPersonalEmailIDByInstituteID(Mockito.<String>any());
        verify(recipientDetailsRepo).findRecipientByInstituteID(Mockito.<String>any());
        verify(recipientDetailsRepo).saveByInstituteID(anyInt(), Mockito.<String>any());
        verify(emailConfig).sendOTPMail(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
        assertEquals("OTP to sent linked emailID successfully!", actualEmailIDByInstituteID);
    }

    /**
     * Method under test: {@link RecipientDetailsImpl#sendMail(String, String)}
     */
    @Test
    void testSendMail() {
        doNothing().when(emailConfig).sendOTPMail(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
        when(recipientDetailsRepo.findRecipientByInstituteID(Mockito.<String>any())).thenReturn(new ArrayList<>());
        recipientDetailsImpl.sendMail("jane.doe@example.org", "Institute ID");
        verify(recipientDetailsRepo).findRecipientByInstituteID(Mockito.<String>any());
        verify(emailConfig).sendOTPMail(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    }

    /**
     * Method under test: {@link RecipientDetailsImpl#sendMail(String, String)}
     */
    @Test
    void testSendMail2() {
        doNothing().when(emailConfig).sendOTPMail(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

        Recipient recipient = new Recipient();
        recipient.setInstituteID("Inside sendMail");
        recipient.setOTP(100000);
        recipient.setOrderID("Inside sendMail");
        recipient.setPersonalEmailID("jane.doe@example.org");
        recipient.setReceived(true);
        recipient.setRecipientFirstName("Jane");
        recipient.setRecipientLastName("Doe");
        recipient.setRecipientPhoneNumber("6625550144");
        recipient.setRetailer("Inside sendMail");

        ArrayList<Recipient> recipientList = new ArrayList<>();
        recipientList.add(recipient);
        doNothing().when(recipientDetailsRepo).saveByInstituteID(anyInt(), Mockito.<String>any());
        when(recipientDetailsRepo.findRecipientByInstituteID(Mockito.<String>any())).thenReturn(recipientList);
        recipientDetailsImpl.sendMail("jane.doe@example.org", "Institute ID");
        verify(recipientDetailsRepo).findRecipientByInstituteID(Mockito.<String>any());
        verify(recipientDetailsRepo).saveByInstituteID(anyInt(), Mockito.<String>any());
        verify(emailConfig).sendOTPMail(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    }

    /**
     * Method under test: {@link RecipientDetailsImpl#searchByInstituteID(String)}
     */
    @Test
    void testSearchByInstituteID() {
        ArrayList<String> stringList = new ArrayList<>();
        when(recipientDetailsRepo.findRecipientDetailsDataByInstituteId(Mockito.<String>any())).thenReturn(stringList);
        List<String> actualSearchByInstituteIDResult = recipientDetailsImpl.searchByInstituteID("Institute ID");
        verify(recipientDetailsRepo).findRecipientDetailsDataByInstituteId(Mockito.<String>any());
        assertTrue(actualSearchByInstituteIDResult.isEmpty());
        assertSame(stringList, actualSearchByInstituteIDResult);
    }

    /**
     * Method under test:
     * {@link RecipientDetailsImpl#searchLogsByInstituteID(String)}
     */
    @Test
    void testSearchLogsByInstituteID() {
        ArrayList<String> stringList = new ArrayList<>();
        when(recipientDetailsRepo.findAllRecipientDetailsByInstituteId(Mockito.<String>any())).thenReturn(stringList);
        List<String> actualSearchLogsByInstituteIDResult = recipientDetailsImpl.searchLogsByInstituteID("Institute ID");
        verify(recipientDetailsRepo).findAllRecipientDetailsByInstituteId(Mockito.<String>any());
        assertTrue(actualSearchLogsByInstituteIDResult.isEmpty());
        assertSame(stringList, actualSearchLogsByInstituteIDResult);
    }

    /**
     * Method under test: {@link RecipientDetailsImpl#saveData(RecipientDetailsDTO)}
     */
    @Test
    void testSaveData() {
        Recipient recipient = new Recipient();
        recipient.setInstituteID("Institute ID");
        recipient.setOTP(1);
        recipient.setOrderID("Order ID");
        recipient.setPersonalEmailID("jane.doe@example.org");
        recipient.setReceived(true);
        recipient.setRecipientFirstName("Jane");
        recipient.setRecipientLastName("Doe");
        recipient.setRecipientPhoneNumber("6625550144");
        recipient.setRetailer("Retailer");
        when(recipientDetailsRepo.save(Mockito.<Recipient>any())).thenReturn(recipient);

        RecipientDetailsDTO recipientDetailsDTO = new RecipientDetailsDTO();
        recipientDetailsDTO.setInstituteID("Institute ID");
        recipientDetailsDTO.setOrderID("Order ID");
        recipientDetailsDTO.setPersonalEmailID("jane.doe@example.org");
        recipientDetailsDTO.setRecipientFirstName("Jane");
        recipientDetailsDTO.setRecipientLastName("Doe");
        recipientDetailsDTO.setRecipientPhoneNumber("6625550144");
        recipientDetailsDTO.setRetailer("Retailer");
        String actualSaveDataResult = recipientDetailsImpl.saveData(recipientDetailsDTO);
        verify(recipientDetailsRepo).save(Mockito.<Recipient>any());
        assertEquals("Order details saved successfully!", actualSaveDataResult);
    }

    /**
     * Method under test:  {@link RecipientDetailsImpl#checkOtp(int, String)}
     */
    @Test
    void testCheckOtp() {
        when(recipientDetailsRepo.findRecipientByInstituteID(Mockito.<String>any())).thenReturn(new ArrayList<>());
        when(recipientDetailsRepo.findOtpByInstituteID(Mockito.<String>any())).thenReturn(1);
        String actualCheckOtpResult = recipientDetailsImpl.checkOtp(1, "Institute ID");
        verify(recipientDetailsRepo).findOtpByInstituteID(Mockito.<String>any());
        verify(recipientDetailsRepo).findRecipientByInstituteID(Mockito.<String>any());
        assertEquals("OTP Verified!", actualCheckOtpResult);
    }

    /**
     * Method under test: {@link RecipientDetailsImpl#checkOtp(int, String)}
     */
    @Test
    void testCheckOtp2() {
        Recipient recipient = new Recipient();
        recipient.setInstituteID("OTP Verification Process");
        recipient.setOTP(1);
        recipient.setOrderID("OTP Verification Process");
        recipient.setPersonalEmailID("jane.doe@example.org");
        recipient.setReceived(true);
        recipient.setRecipientFirstName("Jane");
        recipient.setRecipientLastName("Doe");
        recipient.setRecipientPhoneNumber("6625550144");
        recipient.setRetailer("OTP Verification Process");

        ArrayList<Recipient> recipientList = new ArrayList<>();
        recipientList.add(recipient);
        doNothing().when(recipientDetailsRepo).makeAsReceived(Mockito.<String>any());
        when(recipientDetailsRepo.findRecipientByInstituteID(Mockito.<String>any())).thenReturn(recipientList);
        when(recipientDetailsRepo.findOtpByInstituteID(Mockito.<String>any())).thenReturn(1);
        String actualCheckOtpResult = recipientDetailsImpl.checkOtp(1, "Institute ID");
        verify(recipientDetailsRepo).findOtpByInstituteID(Mockito.<String>any());
        verify(recipientDetailsRepo).findRecipientByInstituteID(Mockito.<String>any());
        verify(recipientDetailsRepo).makeAsReceived(Mockito.<String>any());
        assertEquals("OTP Verified!", actualCheckOtpResult);
    }

    /**
     * Method under test:  {@link RecipientDetailsImpl#checkOtp(int, String)}
     */
    @Test
    void testCheckOtp3() {
        when(recipientDetailsRepo.findOtpByInstituteID(Mockito.<String>any())).thenReturn(0);
        String actualCheckOtpResult = recipientDetailsImpl.checkOtp(1, "Institute ID");
        verify(recipientDetailsRepo).findOtpByInstituteID(Mockito.<String>any());
        assertEquals("OTP not verified!", actualCheckOtpResult);
    }

    /**
     * Method under test:  {@link RecipientDetailsImpl#loginRecipient(String, String)}
     */
    @Test
    void testLoginRecipient() {
        when(recipientDetailsRepo.findPasswordByEmailID(Mockito.<String>any())).thenReturn("jane.doe@example.org");
        when(recipientDetailsRepo.findByEmailID(Mockito.<String>any())).thenReturn("jane.doe@example.org");
        String actualLoginRecipientResult = recipientDetailsImpl.loginRecipient("jane.doe@example.org", "iloveyou");
        verify(recipientDetailsRepo).findByEmailID(Mockito.<String>any());
        verify(recipientDetailsRepo).findPasswordByEmailID(Mockito.<String>any());
        assertEquals("Invalid Login", actualLoginRecipientResult);
    }

    /**
     * Method under test:  {@link RecipientDetailsImpl#loginRecipient(String, String)}
     */
    @Test
    void testLoginRecipient2() {
        when(recipientDetailsRepo.findPasswordByEmailID(Mockito.<String>any())).thenReturn("iloveyou");
        when(recipientDetailsRepo.findByEmailID(Mockito.<String>any())).thenReturn("jane.doe@example.org");
        String actualLoginRecipientResult = recipientDetailsImpl.loginRecipient("jane.doe@example.org", "iloveyou");
        verify(recipientDetailsRepo).findByEmailID(Mockito.<String>any());
        verify(recipientDetailsRepo).findPasswordByEmailID(Mockito.<String>any());
        assertEquals("Valid Login", actualLoginRecipientResult);
    }

    /**
     * Method under test:  {@link RecipientDetailsImpl#loginRecipient(String, String)}
     */
    @Test
    void testLoginRecipient3() {
        when(recipientDetailsRepo.findByEmailID(Mockito.<String>any())).thenReturn(null);
        String actualLoginRecipientResult = recipientDetailsImpl.loginRecipient("jane.doe@example.org", "iloveyou");
        verify(recipientDetailsRepo).findByEmailID(Mockito.<String>any());
        assertEquals("EmailID does not exist. \n Re-check the emailID or contact the admin.", actualLoginRecipientResult);
    }

    /**
     * Method under test: {@link RecipientDetailsImpl#dataPopulate()}
     */
    @Test
    void testDataPopulate() {
        LoginDetails loginDetails = new LoginDetails();
        loginDetails.setEmailID("jane.doe@example.org");
        loginDetails.setPassword("iloveyou");
        when(loginRepo.save(Mockito.<LoginDetails>any())).thenReturn(loginDetails);
        String actualDataPopulateResult = recipientDetailsImpl.dataPopulate();
        verify(loginRepo, atLeast(1)).save(Mockito.<LoginDetails>any());
        assertEquals("Recipient Details Registered!", actualDataPopulateResult);
    }
}
