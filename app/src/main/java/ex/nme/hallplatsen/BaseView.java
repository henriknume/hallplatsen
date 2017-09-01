package ex.nme.hallplatsen;

/**
 * Created by nm2 on 2017-09-02
 */

public interface BaseView<T extends BasePresenter> {

    void setPresenter(T presenter);

}
