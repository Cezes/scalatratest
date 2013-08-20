
import org.scalatra.LifeCycle
import javax.servlet.ServletContext

/**
 * Class which is necessary for Scalatra to work
 * responsible mainly for mounting servlets on server,
 * adding swagger,
 * and appointing paths for automatically generated by swagger docs
 * and for REST api
 */
class ScalatraBootstrap extends LifeCycle {

  implicit val swagger = new DemoSwagger

  override def init(context: ServletContext) {
    context.mount(new DemoService, "/file/*")
    context.mount (new ResourcesApp, "/api-docs/*")
  }
}
