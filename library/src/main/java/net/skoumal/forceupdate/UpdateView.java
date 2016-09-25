package net.skoumal.forceupdate;

/**
 * Created by gingo on 25.9.2016.
 */
public interface UpdateView {

    void showView(Version gCurrentVersion, Version gRequiredVersion, String gUpdateMessage);

}
