package com.example.project3vice;

import com.example.project3vice.vice_classes.ArticleSearchAsyncTask;
import com.example.project3vice.vice_classes.GetLatestByCategoryAsyncTask;
import com.example.project3vice.vice_classes.ViceArticle;
import com.example.project3vice.vice_classes.ViceArticleDBHelper;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

/**
 * Created by Todo on 3/10/2016.
 */
public class ViceAppUnitTests {

    @Test
    public void testCategoryAsyncTaskPullsData(){
        GetLatestByCategoryAsyncTask sync = new GetLatestByCategoryAsyncTask();
        assertNotNull(sync.execute("news"));
    }

    @Test
    public void testArticleAsyncTaskPullsCorrectData(){
        ArticleSearchAsyncTask sync = new ArticleSearchAsyncTask();
        ViceArticle articleTest = new ViceArticle();
        try{articleTest = sync.execute((long)204334).get();}
        catch (Throwable e){e.printStackTrace();}
        String expectedValue = "VICE Staff";
        String actualValue = articleTest.getAuthor();
        assertEquals(expectedValue, actualValue);
    }
}
