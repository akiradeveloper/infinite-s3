package akasha

import java.io.{FileInputStream, File}

import akasha.admin.TestUsers
import com.amazonaws.ClientConfiguration
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.s3.model.S3Object
import com.amazonaws.services.s3.{S3ClientOptions, AmazonS3Client}
import org.apache.commons.io.IOUtils

class AmazonSDKTest extends ServerTestBase {

  def getTestFile(name: String): File = {
    val loader = getClass.getClassLoader
    new File(loader.getResource(name).getFile)
  }

  def checkFileContent(actual: S3Object, expected: File) {
    assert(IOUtils.contentEquals(actual.getObjectContent, new FileInputStream(expected)))
  }

  case class FixtureParam(client: AmazonS3Client)
  override protected def withFixture(test: OneArgTest) = {
    val conf = new ClientConfiguration
    conf.setSignerOverride("S3SignerType") // force to use v2 signature. not supported in 1.9.7

    val cli = new AmazonS3Client(new BasicAWSCredentials(TestUsers.hoge.accessKey, TestUsers.hoge.secretKey), conf)
    cli.setEndpoint(s"http://${server.config.ip}:${server.config.port}")
    cli.setS3ClientOptions(new S3ClientOptions().withPathStyleAccess(true))

    test(FixtureParam(cli))
  }

  import scala.collection.JavaConversions._

  test("add buckets") { p =>
    import p._

    client.createBucket("myb1")
    client.createBucket("myb2")
    val res = client.listBuckets
    assert(res.forall(_.getOwner.getId === TestUsers.hoge.id))
    assert(Set(res(0).getName, res(1).getName) === Set("myb1", "myb2"))
    assert(res.size === 2)

    // location
    // val loc = cli.getBucketLocation("mybucket1")
    // assert(loc == "US")
  }

  test("put and get object") { p =>
    import p._

    client.createBucket("a.b")
    val f = getTestFile("test.txt")
    val putRes = client.putObject("a.b", "myobj.txt", f)
    assert(putRes.getVersionId === "null")

    val obj1 = client.getObject("a.b", "myobj.txt")
    checkFileContent(obj1, f)

    // test second read
    val obj2 = client.getObject("a.b", "myobj.txt")
    checkFileContent(obj2, f)
  }

  test("put and get image object") { p =>
    import p._

    client.createBucket("myb")
    val f = getTestFile("test.jpg")
    client.putObject("myb", "myobj", f)
    val obj = client.getObject("my", "myobj")
    checkFileContent(obj, f)
  }
}
