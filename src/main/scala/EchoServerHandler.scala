import java.util.concurrent.atomic.AtomicLong
import java.util.logging.Level
import java.util.logging.Logger

import org.jboss.netty.buffer.ChannelBuffer
import org.jboss.netty.channel.{ChannelHandlerContext, ExceptionEvent, MessageEvent, SimpleChannelUpstreamHandler}

object EchoServerHandler {
  val LOGGER: Logger = Logger.getLogger(classOf[EchoServerHandler].getName)
}

class EchoServerHandler extends SimpleChannelUpstreamHandler {
  import EchoServerHandler._

  val transferredBytes: AtomicLong = new AtomicLong

  def getTransferredBytes: Long = transferredBytes.get

  override def messageReceived(ctx: ChannelHandlerContext, e: MessageEvent): Unit = {
    transferredBytes.addAndGet(e.getMessage.asInstanceOf[ChannelBuffer].readableBytes)
    e.getChannel.write(e.getMessage)
  }

  override def exceptionCaught(ctx: ChannelHandlerContext, e: ExceptionEvent): Unit = {
    LOGGER.log(Level.WARNING, "Unexcepted exception from downstream.", e.getCause)
    e.getChannel.close()
  }
}

