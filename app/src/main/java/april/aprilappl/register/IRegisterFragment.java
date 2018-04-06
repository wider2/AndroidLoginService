package april.aprilappl.register;

import april.aprilappl.model.ModelRegister;
import april.aprilappl.model.ModelResponse;

interface IRegisterFragment {

    void refreshResult(ModelResponse modelResponse, ModelRegister modelRegister);

    void showException(Exception ex);

    void showErrorServerResponse(Throwable response);

    void statusLoading(int status);
}