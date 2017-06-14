/**
  * Created by ESGI10601 on 13/06/2017.
  */
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.specs2.mutable.Specification
import org.specs2.execute.Pending

@RunWith(classOf[JUnitRunner])
class HelloWorldTest extends Specification {

  "The result of 'hello'" should {
    "say 'Hello World'" in {
      new HelloWorld().hello() must equalTo("Hello World")
    }
    "say 'Hello World'" in {
      Pending
    }
  }
}
