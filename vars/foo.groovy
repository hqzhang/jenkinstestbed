import com.wc.Class
import com.wc.Constants
def call(String str){
    def test =  new Class()
    println Constants.GITHUB_CREDENTIALS_ID
    println Class.MAVEN
    println Class.sayHello(str)
    def ret = test.sayGoodbye(str)
    return ret
}