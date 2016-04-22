package listeners;
//import com.missiondata.fileupload.*;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import org.apache.commons.fileupload.ProgressListener;
/**
 * Session Bean implementation class FileUploadListener
 */
@Stateless
@LocalBean
public class FileUploadListener implements ProgressListener{
	
	private volatile long 
    bytesRead = 0L,
    contentLength = 0L,
    item = 0L;   

  public FileUploadListener() 
 {
    super();
 }

 public void update(long aBytesRead, long aContentLength,
                    int anItem)
 {
    bytesRead = aBytesRead;
    contentLength = aContentLength;
    item = anItem;
    //System.out.println();
 }

 public String getMessage() {
	if (contentLength == -1) {
			return "" + bytesRead + " of Unknown-Total bytes have been read.";
	} else {
		return "" + bytesRead + " of " + contentLength + " bytes have been read ";
	}

 }
 
 public long getBytesRead() 
 {
    return bytesRead;
 }

 public long getContentLength() 
 {
    return contentLength;
 }

 public long getItem() 
 {
    return item;
 }
    /**
     * Default constructor. 
     */
	/*
    public FileUploadListener() {
        // TODO Auto-generated constructor stub
    	super();
    }

    
    private long num100Ks = 0;

	private long theBytesRead = 0;
	private long theContentLength = -1;
	private int whichItem = 0;
	private int percentDone = 0;
	private boolean contentLengthKnown = false;

	public void update(long bytesRead, long contentLength, int items) {

		if (contentLength > -1) {
			contentLengthKnown = true;
		}
		theBytesRead = bytesRead;
		theContentLength = contentLength;
		whichItem = items;

		long nowNum100Ks = bytesRead / 100000;
		// Only run this code once every 100K
		if (nowNum100Ks > num100Ks) {
			num100Ks = nowNum100Ks;
			if (contentLengthKnown) {
				percentDone = (int) Math.round(100.00 * bytesRead / contentLength);
			}
			//System.out.println(getMessage());
		}
	}

	public String getMessage() {
		if (theContentLength == -1) {
			return "" + theBytesRead + " of Unknown-Total bytes have been read.";
		} else {
			return "" + theBytesRead + " of " + theContentLength + " bytes have been read (" + percentDone + "% done).";
		}

	}

	public long getNum100Ks() {
		return num100Ks;
	}

	public void setNum100Ks(long num100Ks) {
		this.num100Ks = num100Ks;
	}

	public long getTheBytesRead() {
		return theBytesRead;
	}

	public void setTheBytesRead(long theBytesRead) {
		this.theBytesRead = theBytesRead;
	}

	public long getTheContentLength() {
		return theContentLength;
	}

	public void setTheContentLength(long theContentLength) {
		this.theContentLength = theContentLength;
	}

	public int getWhichItem() {
		return whichItem;
	}

	public void setWhichItem(int whichItem) {
		this.whichItem = whichItem;
	}

	public int getPercentDone() {
		return percentDone;
	}

	public void setPercentDone(int percentDone) {
		this.percentDone = percentDone;
	}

	public boolean isContentLengthKnown() {
		return contentLengthKnown;
	}

	public void setContentLengthKnown(boolean contentLengthKnown) {
		this.contentLengthKnown = contentLengthKnown;
	}
	*/
}
