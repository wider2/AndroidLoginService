package april.aprilappl.account;

import java.util.List;

import april.aprilappl.model.ModelLogin;


public interface IAccountFragment {

    void refreshResult(ModelLogin modelLogin, List<ModelLogin> listLogins);

    void showException(Exception ex);

    void showErrorServerResponse(Throwable response);

}