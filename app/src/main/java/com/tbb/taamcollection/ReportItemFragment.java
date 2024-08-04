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
                new CreatePDFTask().execute(itemsList);
            }
        });

        return view;
    }

    private class CreatePDFTask extends AsyncTask<List<Item>, Void, PdfDocument> {
        @Override
        protected PdfDocument doInBackground(List<Item>... params) {
            List<Item> itemsList = params[0];
            PdfDocument document = new PdfDocument();
            int page_num = 1;
            PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(584, 440, page_num).create();
            PdfDocument.Page page = document.startPage(pageInfo);

            Canvas canvas = page.getCanvas();
            canvas.drawColor(Color.WHITE);

            Paint paint = new Paint();
            paint.setColor(Color.BLACK);
            paint.setTextSize(42);

            float x = 50;
            float y = 50;
            float imageWidth = 100;
            float imageHeight = 100;

            for (Item item : itemsList) {
                canvas.drawText(item.getName(), x, y, paint);
                y += paint.getTextSize() + 10;

                Bitmap bitmap = getBitmapFromURL(item.getImageUrl());
                if (bitmap != null) {
                    bitmap = Bitmap.createScaledBitmap(bitmap, (int) imageWidth, (int) imageHeight, false);
                    canvas.drawBitmap(bitmap, x, y, paint);
                    y += imageHeight + 20;
                }

                if (y + imageHeight >= pageInfo.getPageHeight()) { // Create a new page if the current page is full
                    document.finishPage(page);
                    pageInfo = new PdfDocument.PageInfo.Builder(595, 842, page_num++).create();
                    page = document.startPage(pageInfo);
                    canvas = page.getCanvas();
                    canvas.drawColor(Color.WHITE);
                    y = 50;
                }
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