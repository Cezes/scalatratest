import akka.actor.{ActorSystem, Props}

import javax.servlet.ServletContext
import org.scalatra.LifeCycle

/**
 * Class which is necessary for Scalatra to work
 * responsible mainly for mounting servlets on server,
 * adding swagger,
 * and appointing paths for automatically generated by swagger docs
 * and for REST api
 */
class ScalatraBootstrap extends LifeCycle {

  implicit val swagger = new DemoSwagger
  val system = ActorSystem()
  val myActor = system.actorOf(Props[MyActor])

  override def init(context: ServletContext) {
    context.mount(new DemoService(system, swagger), "/file/*")
    context.mount (new ResourcesApp, "/api-docs/*")
    context.mount(new MyActorApp(system, myActor), "/actors/*")
  }

  override def destroy(context:ServletContext) {
    system.shutdown()
  }
}
