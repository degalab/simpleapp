package bootstrap.liftweb

import com.dega.simpleapp.Config
import com.dega.simpleapp.api.{CartRest, CouponRest, ProductRest, UserRest}
import com.dega.simpleapp.util.Loggers
import net.liftweb.http.LiftRules

class Boot extends Loggers {

    def boot {
        LiftRules.dispatch.append(ProductRest)
        LiftRules.dispatch.append(CouponRest)
        LiftRules.dispatch.append(CartRest)
        LiftRules.dispatch.append(UserRest)

        Config.setup()
        info("Running")

    }
}