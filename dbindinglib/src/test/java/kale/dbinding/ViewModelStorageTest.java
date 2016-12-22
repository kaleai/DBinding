package kale.dbinding;

import java.util.Map;

import android.databinding.BaseObservable;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import kale.dbinding.data.ObservableCharSequence;

/**
 * @author Kale
 * @date 2016/12/22
 */
public class ViewModelStorageTest {

    @Before
    public void clear() {
        ViewModelStorage.getInstance().getMap().clear();
    }

    @org.junit.Test
    public void putViewModel() throws Exception {
        ViewModelStorage storage = ViewModelStorage.getInstance();
        Map<Integer, BaseObservable> map = storage.getMap();
        Assert.assertEquals(0, map.size());

        ObservableCharSequence viewModel = ObservableCharSequence.create("a");
        int index1 = storage.putViewModel(viewModel);
        int index2 = storage.putViewModel(viewModel);

        Assert.assertEquals(index1, 0);
        Assert.assertEquals(index2, 0);

        Assert.assertEquals(map.size(), 1);

        int index3 = storage.putViewModel(ObservableCharSequence.create("a"));
        Assert.assertEquals(index3, 1);
        Assert.assertEquals(map.size(), 2);

        int index4 = storage.putViewModel(ObservableCharSequence.create("b"));
        Assert.assertEquals(index4, 2);
        Assert.assertEquals(map.size(), 3);

        storage.putViewModel(ObservableCharSequence.create("c"));
        Assert.assertEquals(map.size(), 4);
    }

    @Test
    public void getViewModel() throws Exception {
        ViewModelStorage storage = ViewModelStorage.getInstance();
        Assert.assertEquals(null, storage.getViewModel(100));
        
        Assert.assertEquals(0, storage.getMap().size());

        storage.putViewModel(ObservableCharSequence.create("a"));
        storage.putViewModel(ObservableCharSequence.create("b"));
        storage.putViewModel(ObservableCharSequence.create("c"));

        ObservableCharSequence value1 = (ObservableCharSequence) storage.getViewModel(1);
        assert value1 != null;
        Assert.assertEquals(value1.get(), "b");

        ObservableCharSequence value2 = (ObservableCharSequence) storage.getViewModel(0);
        assert value2 != null;
        Assert.assertEquals(value2.get(), "a");

        Assert.assertEquals(1, storage.getRealSize());

        ObservableCharSequence value3 = (ObservableCharSequence) storage.getViewModel(2);
        assert value3 != null;
        Assert.assertEquals(value3.get(), "c");

        Assert.assertEquals(0, storage.getRealSize());
    }

}