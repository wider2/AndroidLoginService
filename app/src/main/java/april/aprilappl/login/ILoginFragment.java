package april.aprilappl.login;

import april.aprilappl.model.ModelResponse;

public interface ILoginFragment {

    void refreshResult(ModelResponse jsonResponse);

    void showException(Exception ex);

    void showErrorServerResponse(Throwable response);

}