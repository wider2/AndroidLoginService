package april.aprilappl.dagger;

import android.content.Context;

import april.aprilappl.dagger.scope.PerActivityScope;
import dagger.Component;


@PerActivityScope
@Component(modules={LibModule.class})
public interface LibComponent {

    LibRepository getLibRepository();

}