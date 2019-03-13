package hotshot.elick.com.hotshot.baseMVP;

public interface BasePresenter {
    void attachView(BaseView baseView);
    void dettachView(BaseView baseView);
}
