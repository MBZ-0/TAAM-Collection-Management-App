package com.tbb.taamcollection;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.pdf.PdfDocument;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import com.google.android.material.textfield.TextInputEditText;

public class ReportItemFragment extends Fragment {
    public boolean dp = false;
    ItemDatabase db;

    public ReportItemFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.report_item_fragment, container, false);
        db = new ItemDatabase("items");
        Button lotButton = view.findViewById(R.id.lot_submit);
        Button nameButton = view.findViewById(R.id.name_submit);
        Button catButton = view.findViewById(R.id.cat_submit);
        Button catdpButton = view.findViewById(R.id.cat_dpsubmit);
        Button periodButton = view.findViewById(R.id.period_submit);
        Button periodDPButton = view.findViewById(R.id.period_dpsubmit);

        TextInputEditText lotText = view.findViewById(R.id.lot_input);
        TextInputEditText nameText = view.findViewById(R.id.name_input);
        TextInputEditText catText = view.findViewById(R.id.cat_input);
        TextInputEditText catdpText = view.findViewById(R.id.cat_dpinput);
        TextInputEditText periodText = view.findViewById(R.id.period_input);
        TextInputEditText periodDPText = view.findViewById(R.id.period_dpinput);

        Button allButton = view.findViewById(R.id.all_submit);
        Button allDPButton = view.findViewById(R.id.all_dpsubmit);


        lotButton.setOnClickListener(new View.OnClickListener() { // LOT SEARCH
            @Override
            public void onClick(View v) {
                LinkedList<Item> itemsList = db.search("", lotText.getText().toString(), "", "");
                new CreatePDFTask().execute(itemsList);
            }
        });
        nameButton.setOnClickListener(new View.OnClickListener() { // NAME SEARCH
            @Override
            public void onClick(View v) {
                LinkedList<Item> itemsList = db.search(nameText.getText().toString(), "", "", "");
                new CreatePDFTask().execute(itemsList);
            }
        });
        catButton.setOnClickListener(new View.OnClickListener() { // CATEGORY SEARCH
            @Override
            public void onClick(View v) {
                LinkedList<Item> itemsList = db.search("", "", catText.getText().toString(), "");
                new CreatePDFTask().execute(itemsList);
            }
        });
        catdpButton.setOnClickListener(new View.OnClickListener() { // CATEGORY W/ DESC. & PIC
            @Override
            public void onClick(View v) {
                LinkedList<Item> itemsList = db.search("", "", catdpText.getText().toString(), "");
                dp = true;
                new CreatePDFTask().execute(itemsList);
            }
        });
        periodButton.setOnClickListener(new View.OnClickListener() { // PERIOD SEARCH
            @Override
            public void onClick(View v) {
                LinkedList<Item> itemsList = db.search("", "", "", periodText.getText().toString());
                new CreatePDFTask().execute(itemsList);
            }
        });
        periodDPButton.setOnClickListener(new View.OnClickListener() { // PERIOD W/ DESC. & PIC SEARCH
            @Override
            public void onClick(View v) {
                LinkedList<Item> itemsList = db.search("", "", "", periodDPText.getText().toString());
                dp = true;
                new CreatePDFTask().execute(itemsList);
            }
        });

        return view;
    }

    private class CreatePDFTask extends AsyncTask<List<Item>, Void, PdfDocument> {
        @Override
        protected PdfDocument doInBackground(List<Item>... params) {
            List<Item> itemsList = params[0];

            int index = 0;

            PdfDocument document = new PdfDocument();
            int page_num = 1;
            PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(584, 440, page_num).create();
            PdfDocument.Page page = document.startPage(pageInfo);

            Canvas canvas = page.getCanvas();
            canvas.drawColor(Color.WHITE);

            Paint paint = new Paint();
            paint.setColor(Color.BLACK);
            paint.setTextSize(20);
            TextPaint textPaint = new TextPaint();
            textPaint.setColor(Color.BLACK);
            textPaint.setTextSize(20);



            for (Item item : itemsList) {
                if(index != 0){
                    document.finishPage(page);
                    pageInfo = new PdfDocument.PageInfo.Builder(584, 440, page_num++).create();
                    page = document.startPage(pageInfo);
                    canvas = page.getCanvas();
                    canvas.drawColor(Color.WHITE);

                }
                Bitmap bitmap = getBitmapFromURL(item.getImageUrl());
                if (bitmap != null) {
                    bitmap = Bitmap.createScaledBitmap(bitmap, 150, 150, false);
                    canvas.drawBitmap(bitmap,50, 50, paint);

                }
                StaticLayout textBox = new StaticLayout(item.getDescription(), textPaint,200, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false );

                canvas.save();
                canvas.translate(300, 50);
                textBox.draw(canvas);
                canvas.restore();

                if(dp == false) {
                    canvas.drawText("Name: " + item.getName(), 50, 250, paint);
                    canvas.drawText("Category: " + item.getCategory().toString(), 50, 280, paint);
                    canvas.drawText("Name: " + item.getLotNumber(), 50, 310, paint);
                }

                index++;


            }

            document.finishPage(page);
            return document;
        }

        @Override
        protected void onPostExecute(PdfDocument document) {
            String pdfPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
            File file = new File(pdfPath, "Report.pdf");
            try {
                FileOutputStream fos = new FileOutputStream(file);
                document.writeTo(fos);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            document.close();
        }

        private Bitmap getBitmapFromURL(String src) {
            try {
                URL url = new URL(src);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                return BitmapFactory.decodeStream(input);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
    }
}