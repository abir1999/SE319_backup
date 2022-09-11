/*
If Test fails, maven will not build!
 */
package mockito;

import com.example.demo.models.Custom_phrase;
import com.example.demo.repositories.Custom_phraseRepo;
import com.example.demo.services.Custom_phraseService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


public class TestCustom_PhraseService {

    @InjectMocks
    Custom_phraseService service;

    @Mock
    Custom_phraseRepo repo;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getAllPhrasesByUsername(){


        //mock findByUser so that it returns a list of all phrases added by that user.
        Custom_phrase p1 = new Custom_phrase(1,"abir99","Hello!");
        Custom_phrase p2 = new Custom_phrase(2,"abir99","Goodbye");
        Custom_phrase p3 = new Custom_phrase(3,"abir99","Thank You");
        Custom_phrase p4 = new Custom_phrase(4,"abir99","Sorry!");

        List<Custom_phrase> list = Arrays.asList(p1,p2,p3,p4);
        List<Custom_phrase> phraselist = new ArrayList<>();
        phraselist.addAll(list);

        when(repo.findByUser("abir99")).thenReturn(phraselist); //mock the database "offline"

        List<Custom_phrase> outputList = service.findAllPhraseByUser("abir99"); //query the mock database

        assertEquals(4,outputList.size());
        assertEquals("Thank You",outputList.get(2).getPhrase());
        assertEquals("abir99",outputList.get(0).getUsername());

        //verify that findbyUser method in repository is called only once
        verify(repo,times(1)).findByUser("abir99");
        verifyNoMoreInteractions(repo);

    }
}
