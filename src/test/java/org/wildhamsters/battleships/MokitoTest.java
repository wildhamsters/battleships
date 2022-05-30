package org.wildhamsters.battleships;

import static org.testng.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Test
public class MokitoTest {

    @BeforeMethod
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void whenNotUseMockAnnotation() {
        List mockList = Mockito.mock(ArrayList.class);
        mockList.add("one");
        Mockito.verify(mockList).add("one");
        assertEquals(0, mockList.size());
        Mockito.when(mockList.size()).thenReturn(100);
        assertEquals(100, mockList.size());
    }
    @Mock
    List<String> mList;
    @Test
    public void whenUseMockAnnotation() {
        mList.add("one");
        Mockito.verify(mList).add("one");
        assertEquals(0,mList.size());

        Mockito.when(mList.size()).thenReturn(100);
        assertEquals(100, mList.size());
    }
}
