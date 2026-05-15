<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:output method="html" encoding="UTF-8" indent="yes"/>

    <xsl:template match="/">
        <html>
            <head>
                <title>Лекарственные препараты</title>
                <style>
                    body { font-family: Arial, sans-serif; margin: 20px; }
                    h1 { color: #2c3e50; text-align: center; }
                    table { width: 100%; border-collapse: collapse; margin-top: 20px; }
                    th, td { border: 1px solid #ddd; padding: 12px; text-align: left; }
                    th { background-color: #2c3e50; color: white; }
                    tr:nth-child(even) { background-color: #f2f2f2; }
                    .version { font-size: 12px; color: #666; margin-top: 5px; }
                </style>
            </head>
            <body>
                <h1>Список лекарственных препаратов</h1>
                <p>Отсортировано по цене (от дешёвых к дорогим)</p>

                <table>
                    <tr>
                        <th>Название</th>
                        <th>Производитель</th>
                        <th>Группа</th>
                        <th>Цена (мин.)</th>
                        <th>Аналоги</th>
                        <th>Формы выпуска</th>
                    </tr>
                    <xsl:for-each select="medicines/medicine">
                        <xsl:sort select="versions/version/package/price" data-type="number"/>
                        <tr>
                            <td><xsl:value-of select="name"/></td>
                            <td><xsl:value-of select="pharm"/></td>
                            <td><xsl:value-of select="group"/></td>
                            <td style="font-weight: bold; color: #27ae60;">
                                <xsl:value-of select="versions/version[1]/package/price"/> руб.
                            </td>
                            <td>
                                <xsl:for-each select="analogs">
                                    <xsl:value-of select="."/>
                                    <xsl:if test="position() != last()">, </xsl:if>
                                </xsl:for-each>
                            </td>
                            <td>
                                <xsl:for-each select="versions/version">
                                    <div class="version">
                                        📦 <xsl:value-of select="@type"/> -
                                        <xsl:value-of select="dosage/amount"/>
                                        <xsl:value-of select="dosage/unit"/>
                                        (<xsl:value-of select="package/price"/> руб.)
                                    </div>
                                </xsl:for-each>
                            </td>
                        </tr>
                    </xsl:for-each>
                </table>
            </body>
        </html>
    </xsl:template>

</xsl:stylesheet>